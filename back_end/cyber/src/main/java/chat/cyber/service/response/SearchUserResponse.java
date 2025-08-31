package chat.cyber.service.response;

import java.util.UUID;

public class SearchUserResponse {

    private String name;
    private String email;
    private UUID idPublic;

    public SearchUserResponse(String name, String email, UUID idPublic) {
        this.name = name;
        this.email = email;
        this.idPublic = idPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
