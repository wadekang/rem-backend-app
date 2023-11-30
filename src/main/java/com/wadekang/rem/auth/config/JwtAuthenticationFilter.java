package com.wadekang.rem.auth.config;

import com.wadekang.rem.auth.jwt.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = extractJwtToken(request);

        if (accessToken != null) {
            Claims claims = jwtTokenValidator.validateToken(accessToken);
            Authentication authentication = createAuthentication(claims);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest request) {

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("access_token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    private Authentication createAuthentication(Claims claims) {

        String username = claims.getSubject();

        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null, null);
        }

        return null;
    }
}
