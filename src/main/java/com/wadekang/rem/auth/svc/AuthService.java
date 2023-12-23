package com.wadekang.rem.auth.svc;

import com.wadekang.rem.auth.vo.LoginJwtResponse;
import com.wadekang.rem.auth.vo.LoginRequest;
import com.wadekang.rem.vo.CreateUserVO;
import com.wadekang.rem.vo.UserResponseVO;
import jakarta.transaction.NotSupportedException;
import org.springframework.security.core.context.SecurityContextHolder;

public interface AuthService {

    /**
     * 로그인 처리
     * - 로그인 성공 시, Access Token과 Refresh Token을 발급하여 Http Only Cookie로 헤더에 저장
     * - 유저 정보 반환
     * @param loginRequest
     * @return
     */
    LoginJwtResponse login(LoginRequest loginRequest, String provider) throws NotSupportedException;

    void signUp(CreateUserVO createUserVO, String provider);

    /**
     * Spring Security의 JwtAuthenticationFilter에서 유효한 토큰인지 검증 후
     * 메서드로 넘어오기 때문에 userId로 유저 정보를 조회하여 반환
     * @return
     */
    UserResponseVO isTokenValid();

    static Long getUserIdFromContext() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
