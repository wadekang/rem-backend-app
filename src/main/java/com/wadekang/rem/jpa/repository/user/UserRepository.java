package com.wadekang.rem.jpa.repository.user;

import com.wadekang.rem.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    @Query("select u from User u where u.loginId = :loginId")
    Optional<User> findByLoginId(@Param("loginId") String loginId);
}
