package com.wadekang.rem.jpa.svc;

import com.wadekang.rem.jpa.domain.User;
import com.wadekang.rem.jpa.repository.user.UserRepository;
import com.wadekang.rem.jpa.vo.CreateUserVO;
import com.wadekang.rem.jpa.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }

    @Override
    public UserResponseVO findByUserId(Long userId) {

        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Not Exist User"));
    }

    @Override
    public UserResponseVO findByLoginId(String loginId) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Not Exist User"));

        return new UserResponseVO(user);
    }

    @Override
    public UserResponseVO findByoAuthUserId(String oAuthUserId) {

        return userRepository.findByoAuthUserId(oAuthUserId)
                .orElseThrow(() -> new IllegalArgumentException("Not Exist User"));
    }

    @Override
    public UserResponseVO createUser(CreateUserVO user) {

        User newUser = User.createUserBuilder()
                .createUser(user)
                .build();

        User createdUser = userRepository.save(newUser);

        return new UserResponseVO(createdUser);
    }
}
