package com.wadekang.rem.jpa.repository.user;

import com.wadekang.rem.jpa.vo.UserResponseVO;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<UserResponseVO> findByUserId(Long userId);

    Optional<UserResponseVO> findByoAuthUserId(String oAuthUserId);


}
