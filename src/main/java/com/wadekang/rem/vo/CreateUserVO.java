package com.wadekang.rem.vo;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserVO {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private String profileImageUrl;
    private String oAuthUserId;
    private String oAuthProvider;

    @Builder(builderClassName = "CreateGoogleUserBuilder", builderMethodName = "CreateGoogleUserBuilder")
    public CreateUserVO(GoogleIdToken.Payload payload) {
        this.loginId = payload.getEmail();
        this.name = (String) payload.get("name");
        this.email = payload.getEmail();
        this.profileImageUrl = (String) payload.get("picture");
        this.oAuthProvider = "google";
        this.oAuthUserId = (String) payload.get("sub");
    }

    @Builder(builderClassName = "CreateUserBuilder", builderMethodName = "CreateUserBuilder")
    public CreateUserVO(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}
