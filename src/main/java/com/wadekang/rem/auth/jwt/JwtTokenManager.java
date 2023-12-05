package com.wadekang.rem.auth.jwt;

import com.wadekang.rem.auth.vo.JwtTokenVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class JwtTokenManager {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtAccessExpirationInMs}")
    private long jwtAccessExpirationInMs;

    @Value("${app.jwtRefreshExpirationInMs}")
    private long jwtRefreshExpirationInMs;

    public JwtTokenVO generateAccessToken(Long userId) {

        return getJwtTokenVO(userId, jwtAccessExpirationInMs);
    }

    public JwtTokenVO generateRefreshToken(Long userId) {

        return getJwtTokenVO(userId, jwtRefreshExpirationInMs);
    }

    private JwtTokenVO getJwtTokenVO(Long userId, long expirationInMs) {

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + expirationInMs);

        String jwtToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return new JwtTokenVO(jwtToken, expirationInMs);
    }

    public Claims reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = extractJwtToken(request, "refresh_token");

        if (refreshToken == null || !isTokenNonExpired(refreshToken)) return null;

        Claims claims = validateToken(refreshToken);
        JwtTokenVO accessToken = generateAccessToken(Long.parseLong(claims.getSubject()));

        Cookie cookie = new Cookie("access_token", accessToken.getToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) accessToken.getExpirationTime());
        cookie.setPath("/");
        response.addCookie(cookie);

        return claims;
    }

    public Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenNonExpired(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public String extractJwtToken(HttpServletRequest request, String tokenName) {

        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(tokenName))
                .map(Cookie::getValue)
                .findAny()
                .orElse(null);
    }
}
