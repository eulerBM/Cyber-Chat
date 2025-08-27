package chat.cyber.service.response;

import java.util.UUID;

public class SearchUserResponse {

    private String email;
    private UUID idPublic;

    public SearchUserResponse(String email, UUID idPublic) {
        this.email = email;
        this.idPublic = idPublic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getIdPublic() {
        return idPublic;
    }

    public void setIdPublic(UUID idPublic) {
        this.idPublic = idPublic;
    }
}
