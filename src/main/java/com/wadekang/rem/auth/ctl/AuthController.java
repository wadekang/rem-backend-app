package com.wadekang.rem.auth.ctl;

import com.wadekang.rem.auth.jwt.JwtTokenManager;
import com.wadekang.rem.auth.svc.AuthService;
import com.wadekang.rem.auth.vo.LoginRequest;
import com.wadekang.rem.common.vo.CommonResponse;
import com.wadekang.rem.domain.User;
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

    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Object>> login(@RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getLoginId(), loginRequest.getPassword());
        authenticationManager.authenticate(authenticationRequest);

        User user = authService.loadUserByUsername(loginRequest.getLoginId());
        String accessToken = jwtTokenManager.generateToken(user.getUserId());

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
                                "Successfully logged in",
                                null
                        )
                );
    }

}
