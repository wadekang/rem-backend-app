package com.wadekang.rem.auth.ctl;

import com.wadekang.rem.auth.jwt.JwtTokenProvider;
import com.wadekang.rem.auth.vo.LoginRequest;
import com.wadekang.rem.auth.vo.LoginResponseJwt;
import com.wadekang.rem.common.vo.CommonResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponseJwt>> login(@RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(authenticationRequest);

        String accessToken = jwtTokenProvider.generateToken(loginRequest.getUsername());

        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .maxAge(Duration.ofHours(1))
                .path("/")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(
                        new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "OK",
                                null
                        )
                );
    }

    @GetMapping("/logout")
    public ResponseEntity<CommonResponse<?>> logout() {

        return ResponseEntity.ok()
                .body(
                        new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "Successfully logged out",
                                null
                        )
                );
    }

}
