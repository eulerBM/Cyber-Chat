package chat.cyber.service.response;

import chat.cyber.entity.Message;

import java.util.List;

public class MessagesChatResponse {

    private List<Message> message;

    public MessagesChatResponse(List<Message> message) {
        this.message = message;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
}
