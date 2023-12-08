package com.wadekang.rem.jpa.domain;

import com.wadekang.rem.jpa.vo.CreateUserVO;
import jakarta.persistence.*;
import lombok.Builder;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "tb_user_seq", allocationSize = 1)
    private Long userId;

    @Column(name = "login_id", unique = true, nullable = false, length = 50)
    private String loginId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "phone", length = 50) // phoneNumber 필요하지 않을 것 같아서 일단 nullable true로 변경
    private String phone;

    @Column(name = "profile_image_url", length = 200)
    private String profileImageUrl;

    @Column(name = "oauth_provider", length = 50)
    private String oAuthProvider;

    @Column(name = "oauth_user_id", unique = true, length = 100)
    private String oAuthUserId;

    @Column(name = "is_account_non_locked", nullable = false, columnDefinition = "boolean default true")
    private Boolean isAccountNonLocked;

    @Column(name = "password_error_count", nullable = false, columnDefinition = "int default 0")
    private int passwordErrorCount;

    // ROLE_ADMIN, ROLE_USER
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20, columnDefinition = "varchar(20) default 'ROLE_USER'")
    private Role role;

    public User(Long userId, String loginId, String password, Role role) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
        this.isAccountNonLocked = true;
    }

    @Builder(builderClassName = "CreateUserBuilder", builderMethodName = "createUserBuilder")
    public User(CreateUserVO createUser) {
        this.loginId = createUser.getLoginId();
        this.name = createUser.getName();
        this.password = createUser.getPassword();
        this.email = createUser.getEmail();
        this.profileImageUrl = createUser.getProfileImageUrl();
        this.oAuthProvider = createUser.getOAuthProvider();
        this.oAuthUserId = createUser.getOAuthUserId();
        this.isAccountNonLocked = true;
        this.passwordErrorCount = 0;
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
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
