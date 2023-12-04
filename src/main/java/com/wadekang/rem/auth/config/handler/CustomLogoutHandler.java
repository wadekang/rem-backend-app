package com.wadekang.rem.auth.config.handler;

import com.wadekang.rem.auth.jwt.JwtTokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtTokenManager jwtTokenManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String jwtToken = jwtTokenManager.extractJwtToken(request);
        Claims claims = jwtTokenManager.validateToken(jwtToken);

        if (claims != null)
            log.info("Request - Addr : {}, URI : {}, userId : {}", request.getRemoteAddr(), request.getRequestURI(), claims.getSubject());

        request.getSession().invalidate();
        deleteCookies(response);
    }

    private void deleteCookies(HttpServletResponse response) {

        deleteCookie(response, "JSESSIONID");
        deleteCookie(response, "access_token");
    }

    private void deleteCookie(HttpServletResponse response, String cookieName) {

        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
