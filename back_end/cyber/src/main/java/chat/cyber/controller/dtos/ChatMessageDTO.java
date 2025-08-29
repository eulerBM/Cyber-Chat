package chat.cyber.controller.dtos;

import chat.cyber.entity.Message;

import java.util.UUID;

public class ChatMessageDTO {

    private UUID chatIdPublic;
    private String senderId;
    private String receiverId;
    private String content;

    public UUID getChatIdPublic() {
        return chatIdPublic;
    }

    public void setChatIdPublic(UUID chatIdPublic) {
        this.chatIdPublic = chatIdPublic;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
