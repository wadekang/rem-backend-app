package com.wadekang.rem.svc;

import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.vo.CreateUserVO;
import com.wadekang.rem.vo.UserResponseVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username);

    UserResponseVO findByUserId(Long userId);

    UserResponseVO findByLoginId(String loginId);

    UserResponseVO findByoAuthUserId(String oAuthUserId);

    UserResponseVO createUser(CreateUserVO user);
}
