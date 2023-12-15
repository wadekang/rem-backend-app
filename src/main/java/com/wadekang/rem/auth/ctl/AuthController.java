package com.wadekang.rem.auth.ctl;

import com.wadekang.rem.auth.svc.AuthService;
import com.wadekang.rem.auth.vo.*;
import com.wadekang.rem.common.vo.CommonResponse;
import com.wadekang.rem.vo.UserResponseVO;
import jakarta.transaction.NotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/{provider}")
    public ResponseEntity<CommonResponse<UserResponseVO>> login
            (@PathVariable("provider") String provider,
             @RequestBody LoginRequest loginRequest) throws NotSupportedException {

        LoginJwtResponse loginJwtResponse = authService.login(loginRequest, provider);

        return ResponseEntity.ok()
                .headers(generateHeadersWithJwtCookie(loginJwtResponse))
                .body(
                        new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "Successfully logged in",
                                loginJwtResponse.getUser()
                        )
                );
    }

    @GetMapping("/isTokenValid")
    public ResponseEntity<CommonResponse<UserResponseVO>> isTokenValid() {

        UserResponseVO userResponseVO = authService.isTokenValid();

        return ResponseEntity.ok()
                .body(
                        new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "Valid",
                                userResponseVO
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

    /**
     * Cookie를 포함한 헤더 생성
     * @param response
     * @return
     */
    private HttpHeaders generateHeadersWithJwtCookie(LoginJwtResponse response) {

            ResponseCookie accessToken = generateCookie("access_token", response.getAccessToken());
            ResponseCookie refreshToken = generateCookie("refresh_token", response.getRefreshToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, accessToken.toString());
            headers.add(HttpHeaders.SET_COOKIE, refreshToken.toString());

            return headers;
    }
}
