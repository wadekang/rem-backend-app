package com.wadekang.rem.auth.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequest {

    private String username;
    private String password;

}
