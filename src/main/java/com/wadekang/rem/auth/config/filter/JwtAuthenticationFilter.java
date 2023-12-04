package com.wadekang.rem.auth.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wadekang.rem.auth.jwt.JwtTokenManager;
import com.wadekang.rem.common.vo.CommonResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

        String accessToken = jwtTokenManager.extractJwtToken(request);

        if (accessToken != null) {
            try {
                Claims claims = jwtTokenManager.validateToken(accessToken);
                log.info("Request - Addr : {}, URI : {}, userId : {}", request.getRemoteAddr(), request.getRequestURI(), claims.getSubject());

                Authentication authentication = createAuthentication(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                log.info("Request (Expired Token) - Addr : {}, URI : {}", request.getRemoteAddr(), request.getRequestURI());

                handleExpiredToken(response);
                return;
            }
        } else {
            log.info("Request - Addr : {}, URI : {}", request.getRemoteAddr(), request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(Claims claims) {

        String userId = claims.getSubject();

        if (userId != null) {
            return new UsernamePasswordAuthenticationToken(userId, null, null);
        }

        return null;
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {

        CommonResponse<Object> commonResponse = new CommonResponse<>(HttpServletResponse.SC_UNAUTHORIZED, "Expired Token", null);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
    }
}
