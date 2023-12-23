package com.wadekang.rem.auth.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wadekang.rem.auth.jwt.JwtTokenManager;
import com.wadekang.rem.common.vo.CommonResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isPermittedUrl(request.getRequestURI())) {
            log.info("Request (Permitted URL) - From : {}, URI : {}", request.getRemoteHost(), request.getRequestURI());

            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtTokenManager.extractJwtToken(request, "access_token");

        Claims claims;
        if (accessToken != null && jwtTokenManager.isTokenNonExpired(accessToken)) {
            claims = jwtTokenManager.validateToken(accessToken);
        } else { // Access Token 없거나 만료된 경우
            claims = jwtTokenManager.reissue(request, response); // Refresh Token으로 Access Token 재발급

            if (claims == null) { // Refresh Token 없거나 만료된 경우
                handleUnauthorized(response);
                return;
            }
        }

        log.info("Request - From : {}, URI : {}, userId : {}", request.getRemoteHost(), request.getRequestURI(), claims.getSubject());

        Authentication authentication = createAuthentication(claims);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * JWT 토큰에서 userId 추출하여 Authentication 객체 생성
     * @param claims
     * @return
     */
    private Authentication createAuthentication(Claims claims) {

        String userId = claims.getSubject();

        if (userId != null) {
            return new UsernamePasswordAuthenticationToken(userId, null, null);
        }

        return null;
    }

    /**
     * 인증되지 않은 사용자에게 401 Unauthorized 에러 반환
     * @param response
     * @throws IOException
     */
    private void handleUnauthorized(HttpServletResponse response) throws IOException {

        CommonResponse<Object> commonResponse = new CommonResponse<>(HttpServletResponse.SC_UNAUTHORIZED, "Not Authorized", null);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
    }

    /**
     * 로그인 API는 인증 필터를 타지 않도록 예외 처리
     * @param requestURI
     * @return
     */
    private boolean isPermittedUrl(String requestURI) {

        return requestURI.startsWith("/api/auth/login") || requestURI.startsWith("/api/auth/signUp");
    }
}
