package com.wadekang.rem.auth.ctl;

import com.wadekang.rem.auth.svc.AuthService;
import com.wadekang.rem.auth.vo.JwtTokenVO;
import com.wadekang.rem.auth.vo.LoginJwtResponse;
import com.wadekang.rem.auth.vo.LoginRequest;
import com.wadekang.rem.common.vo.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Object>> login(@RequestBody LoginRequest loginRequest) {

        LoginJwtResponse response = authService.login(loginRequest);

        ResponseCookie accessToken = generateCookie("access_token", response.getAccessToken());
        ResponseCookie refreshToken = generateCookie("refresh_token", response.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshToken.toString());

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

    @GetMapping("/isValid")
    public ResponseEntity<CommonResponse<Object>> isValid() {

        return ResponseEntity.ok()
                .body(
                        new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "Valid",
                                null
                        )
                );
    }

    /**
     * Cookie 생성
     * @param cookieName
     * @param jwtTokenVO
     * @return
     */
    private ResponseCookie generateCookie(String cookieName, JwtTokenVO jwtTokenVO) {

        return ResponseCookie.from(cookieName, jwtTokenVO.getToken())
                .httpOnly(true)
                .maxAge(jwtTokenVO.getExpirationTime() / 1000)
                .path("/")
                .build();
    }
}
