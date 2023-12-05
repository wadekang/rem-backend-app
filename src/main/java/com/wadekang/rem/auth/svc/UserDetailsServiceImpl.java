package com.wadekang.rem.auth.svc;

import com.wadekang.rem.domain.User;
import com.wadekang.rem.repository.UserRepositoryMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryMap userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }
}
