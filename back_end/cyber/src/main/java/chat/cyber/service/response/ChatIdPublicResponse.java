package chat.cyber.service.response;

import java.util.UUID;

public class ChatIdPublicResponse {

    private UUID idPublicChat;

    public ChatIdPublicResponse(UUID idPublicChat) {
        this.idPublicChat = idPublicChat;
    }

    public UUID getIdPublicChat() {
        return idPublicChat;
    }

    public void setIdPublicChat(UUID idPublicChat) {
        this.idPublicChat = idPublicChat;
    }
}
