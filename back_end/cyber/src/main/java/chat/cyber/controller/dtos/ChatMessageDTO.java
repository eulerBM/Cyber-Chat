package chat.cyber.controller.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessageDTO {

    private UUID chatIdPublic;
    private UUID senderId;
    private UUID receiverId;
    private String content;
    private LocalDateTime date;

    public UUID getChatIdPublic() {
        return chatIdPublic;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setChatIdPublic(UUID chatIdPublic) {
        this.chatIdPublic = chatIdPublic;
    }



    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
