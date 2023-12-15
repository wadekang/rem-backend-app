package com.wadekang.rem.common.ctl;

import com.wadekang.rem.common.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = {"com.wadekang.rem.ctl"})
public class CommonControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<CommonResponse<?>> handler(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .ok()
                .body(
                        new CommonResponse<>(
                                500,
                                "Internal server error",
                                null
                        )
                );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<?>> bindExceptionHandler(BindException e) {
        log.error(e.getMessage(), e);

        Map<String, String> res = new HashMap<>();

        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(error -> {
            String field = ((FieldError) error).getField();
            res.put(field, error.getDefaultMessage());
        });

        return ResponseEntity
                .ok()
                .body(
                        new CommonResponse<>(
                                400,
                                "Bad Request - Invalid input",
                                res
                        )
                );
    }
}
