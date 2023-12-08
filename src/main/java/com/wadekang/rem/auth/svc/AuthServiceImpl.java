package com.wadekang.rem.auth.svc;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.wadekang.rem.auth.jwt.JwtTokenManager;
import com.wadekang.rem.auth.vo.JwtTokenVO;
import com.wadekang.rem.auth.vo.LoginJwtResponse;
import com.wadekang.rem.auth.vo.LoginRequest;
import com.wadekang.rem.jpa.vo.CreateUserVO;
import com.wadekang.rem.jpa.vo.UserResponseVO;
import com.wadekang.rem.jpa.svc.UserService;
import jakarta.transaction.NotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final UserService userService;

    @Override
    public LoginJwtResponse login(LoginRequest loginRequest, String provider) throws NotSupportedException {

        return switch (provider) {
            case "form" -> formLogin(loginRequest);
            case "google" -> googleLogin(loginRequest);
            default -> throw new NotSupportedException("Not Supported Provider");
        };
    }

    // TODO 회원 가입 메서드 구현
    @Override
    public UserResponseVO signUp(CreateUserVO createUserVO, String provider) {
        return null;
    }

    @Override
    public UserResponseVO isTokenValid() {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userService.findByUserId(Long.parseLong(userId));
    }

    private LoginJwtResponse formLogin(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getLoginId(), loginRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);

        UserResponseVO user = userService.findByLoginId(loginRequest.getLoginId());

        return generateLoginJwtResponse(user);
    }

    private LoginJwtResponse googleLogin(LoginRequest loginRequest) {

        GoogleIdToken idToken;
        try {
            idToken = googleIdTokenVerifier.verify(loginRequest.getCredential());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();

            UserResponseVO user;
            try {
                user = userService.findByoAuthUserId(userId);
            } catch (IllegalArgumentException e) {
                user = googleSignUp(payload);
            }

            return generateLoginJwtResponse(user);
        }

        throw new RuntimeException("Google Login Failed");
    }

    public UserResponseVO googleSignUp(GoogleIdToken.Payload payload) {

        CreateUserVO newGoogleUser = CreateUserVO.CreateGoogleUserBuilder()
                .payload(payload)
                .build();

        return userService.createUser(newGoogleUser);
    }

    private LoginJwtResponse generateLoginJwtResponse(UserResponseVO user) {

        JwtTokenVO accessToken = jwtTokenManager.generateAccessToken(user.getUserId());
        JwtTokenVO refreshToken = jwtTokenManager.generateRefreshToken(user.getUserId());

        return new LoginJwtResponse(accessToken, refreshToken, user);
    }
}


