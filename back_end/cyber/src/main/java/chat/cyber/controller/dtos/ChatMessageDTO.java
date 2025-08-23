package chat.cyber.controller.dtos;

import chat.cyber.entity.Message;

import java.time.LocalDateTime;

public class ChatMessageDTO {

    private String idPublicUserSend;
    private String idPublicUserReceived;
    private Message message;

    public String getIdPublicUserSend() {
        return idPublicUserSend;
    }

    public void setIdPublicUserSend(String idPublicUserSend) {
        this.idPublicUserSend = idPublicUserSend;
    }

    public String getIdPublicUserReceived() {
        return idPublicUserReceived;
    }

    public void setIdPublicUserReceived(String idPublicUserReceived) {
        this.idPublicUserReceived = idPublicUserReceived;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
