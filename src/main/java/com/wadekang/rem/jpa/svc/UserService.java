package com.wadekang.rem.jpa.svc;

import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.jpa.vo.CreateUserVO;
import com.wadekang.rem.jpa.vo.UserResponseVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username);

    UserResponseVO findByUserId(Long userId);

    UserResponseVO findByLoginId(String loginId);

    UserResponseVO findByoAuthUserId(String oAuthUserId);

    UserResponseVO createUser(CreateUserVO user);
}
