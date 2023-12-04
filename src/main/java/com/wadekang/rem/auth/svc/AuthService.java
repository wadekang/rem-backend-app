package com.wadekang.rem.auth.svc;

import com.wadekang.rem.auth.repo.AuthRepositoryMap;
import com.wadekang.rem.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepositoryMap authRepositoryMap;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepositoryMap.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }
}
