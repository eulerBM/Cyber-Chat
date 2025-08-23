package chat.cyber.service.response;

import java.util.UUID;

public class LoginUserResponse {

    private String accessToken;
    private UserInfoResponse user;

    public LoginUserResponse(String accessToken, UserInfoResponse user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserInfoResponse getUser() {
        return user;
    }
}


