package com.wadekang.rem.svc;

import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.jpa.repository.user.UserRepository;
import com.wadekang.rem.vo.CreateUserVO;
import com.wadekang.rem.vo.UserResponseVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }

    @Override
    public UserResponseVO findByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not Exist User"));

        return new UserResponseVO(user);
    }

    @Override
    public UserResponseVO findByLoginId(String loginId) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("Not Exist User"));

        return new UserResponseVO(user);
    }

    @Override
    public UserResponseVO findByoAuthUserId(String oAuthUserId) {

        User user = userRepository.findByoAuthUserId(oAuthUserId)
                .orElseThrow(() -> new EntityNotFoundException("Not Exist User"));

        return new UserResponseVO(user);
    }

    @Override
    @Transactional
    public UserResponseVO createUser(CreateUserVO user) {

        User newUser = User.createUserBuilder()
                .createUser(user)
                .build();

        User createdUser = userRepository.save(newUser);

        return new UserResponseVO(createdUser);
    }
}
