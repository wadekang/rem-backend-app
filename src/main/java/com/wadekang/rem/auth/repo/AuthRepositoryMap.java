package com.wadekang.rem.auth.repo;

import com.wadekang.rem.domain.Role;
import com.wadekang.rem.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryMap {

    private final HashMap<String, User> users = new HashMap<>();
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        users.put("user1@test.com", new User(1L, "user1@test.com", this.passwordEncoder.encode("password1"), Role.USER));
        users.put("user2@test.com", new User(2L, "user2@test.com", this.passwordEncoder.encode("password2"), Role.USER));
        users.put("user3@test.com", new User(3L, "user3@test.com", this.passwordEncoder.encode("password3"), Role.USER));
    }

    public Optional<User> findByUserName(String loginId) {
        return Optional.ofNullable(users.get(loginId));
    }

}
