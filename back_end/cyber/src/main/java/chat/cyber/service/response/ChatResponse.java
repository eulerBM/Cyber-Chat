package chat.cyber.service.response;

import chat.cyber.entity.Chat;
import chat.cyber.entity.Message;

import java.util.List;
import java.util.UUID;

public class ChatResponse {

    private UUID idPublicChat;

    public ChatResponse(UUID idPublicChat) {
        this.idPublicChat = idPublicChat;

    }

    public UUID getIdPublicChat() {
        return idPublicChat;
    }

    public void setIdPublicChat(UUID idPublicChat) {
        this.idPublicChat = idPublicChat;
    }
}
