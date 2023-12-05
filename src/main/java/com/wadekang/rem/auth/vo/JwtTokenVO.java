package com.wadekang.rem.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenVO {

    private String token;
    private long expirationTime;

}
