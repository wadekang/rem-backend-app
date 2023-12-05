package com.wadekang.rem.auth.svc;

import com.wadekang.rem.auth.vo.LoginJwtResponse;
import com.wadekang.rem.auth.vo.LoginRequest;

public interface AuthService {

    /**
     * 로그인 처리
     * - 로그인 성공 시, Access Token과 Refresh Token을 발급하여 Http Only Cookie로 헤더에 저장
     * @param loginRequest
     * @return
     */
    LoginJwtResponse login(LoginRequest loginRequest);
}
