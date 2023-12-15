package com.wadekang.rem.auth.vo;

import com.wadekang.rem.vo.UserResponseVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginJwtResponse {

    private JwtTokenVO accessToken;
    private JwtTokenVO refreshToken;
    private UserResponseVO user;

}
