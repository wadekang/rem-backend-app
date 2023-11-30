package com.wadekang.rem.auth.ctl;

import com.wadekang.rem.common.vo.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.wadekang.rem.auth.ctl")
public class AuthControllerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse<?>> handler(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new CommonResponse<>(
                                HttpStatus.UNAUTHORIZED.value(),
                                "Invalid username or password",
                                null
                        )
                );
    }
}
