package com.wadekang.rem.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginJwtResponse {

    private JwtTokenVO accessToken;
    private JwtTokenVO refreshToken;
}
