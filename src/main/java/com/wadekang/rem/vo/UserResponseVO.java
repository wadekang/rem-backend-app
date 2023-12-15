package com.wadekang.rem.vo;

import com.wadekang.rem.jpa.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseVO {

    private Long userId;
    private String name;
    private String email;
    private String profileImageUrl;

    @Builder
    public UserResponseVO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
