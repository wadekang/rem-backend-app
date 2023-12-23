package com.wadekang.rem.jpa.domain;

import com.wadekang.rem.vo.CreateUserVO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_user", schema = "rem_schema")
@NoArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendars = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarUser> calendarUsers = new ArrayList<>();

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

        addDefaultCalendar();
    }

    /**
     * 회원 가입 시 기본 캘린더 생성
     */
    private void addDefaultCalendar() {

        Calendar defaultCalendar = Calendar.createDefaultCalendarBuilder()
                .user(this)
                .build();

        CalendarUser defaultCalendarUser = CalendarUser.createDefaultCalenderUserBuilder()
                .calendar(defaultCalendar)
                .user(this)
                .build();

        this.calendars.add(defaultCalendar);
        this.calendarUsers.add(defaultCalendarUser);
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
