package com.wadekang.rem.common.ctl;

import com.wadekang.rem.common.vo.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/hello")
    public ResponseEntity<CommonResponse<String>> hello() {
        return ResponseEntity
                .ok()
                .body(
                        new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "OK",
                                "Hello, World!"
                        )
                );
    }
}
