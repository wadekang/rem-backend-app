package com.wadekang.rem.auth.svc;

import com.wadekang.rem.auth.jwt.JwtTokenManager;
import com.wadekang.rem.auth.vo.JwtTokenVO;
import com.wadekang.rem.auth.vo.LoginJwtResponse;
import com.wadekang.rem.auth.vo.LoginRequest;
import com.wadekang.rem.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;

    @Override
    public LoginJwtResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getLoginId(), loginRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);

        User user = (User) userDetailsService.loadUserByUsername(loginRequest.getLoginId());

        JwtTokenVO accessToken = jwtTokenManager.generateAccessToken(user.getUserId());
        JwtTokenVO refreshToken = jwtTokenManager.generateRefreshToken(user.getUserId());

        return new LoginJwtResponse(accessToken, refreshToken);
    }


}
