package com.wadekang.rem.auth.ctl;

import com.wadekang.rem.common.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.wadekang.rem.auth.ctl")
public class AuthControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handler(Exception e) {

        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new CommonResponse<>(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal server error",
                                null
                        )
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse<?>> handler(BadCredentialsException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new CommonResponse<>(
                                HttpStatus.UNAUTHORIZED.value(),
                                "Invalid username or password",
                                null
                        )
                );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handler(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new CommonResponse<>(
                                HttpStatus.UNAUTHORIZED.value(),
                                e.getMessage(),
                                null
                        )
                );
    }
}
