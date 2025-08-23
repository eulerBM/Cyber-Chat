package chat.cyber.service.response;

import java.util.UUID;

public class UserInfoResponse {

    private UUID idPublic;
    private String name;
    private String email;

    public UserInfoResponse(UUID idPublic, String name, String email) {
        this.idPublic = idPublic;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public UUID getIdPublic() {
        return idPublic;
    }

    public String getEmail() {
        return email;
    }
}
