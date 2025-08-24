package chat.cyber.service.response;

import java.util.UUID;

public class LoginUserResponse {

    private String accessToken;
    private String refreshToken;
    private UserInfoResponse user;

    public LoginUserResponse(String accessToken, String refreshToken, UserInfoResponse user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserInfoResponse getUser() {
        return user;
    }
}


