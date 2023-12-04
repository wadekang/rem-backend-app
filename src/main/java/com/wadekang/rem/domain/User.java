package com.wadekang.rem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Entity
@Table(name = "tb_user", schema = "rem_schema")
@NoArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id", unique = true, nullable = false, length = 50)
    private String loginId;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "phone", unique = true, nullable = false, length = 50)
    private String phone;

    @Column(name = "oauth_provider", length = 50)
    private String oauthProvider;

    @Column(name = "oauth_user_id", length = 50)
    private String oauthUserId;

    // ROLE_ADMIN, ROLE_USER
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20, columnDefinition = "varchar(20) default 'ROLE_USER'")
    private Role role;

    public User(Long userId, String loginId, String password, Role role) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
