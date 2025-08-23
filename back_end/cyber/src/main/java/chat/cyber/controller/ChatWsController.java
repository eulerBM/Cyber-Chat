package chat.cyber.controller;

import chat.cyber.controller.dtos.ChatMessageDTO;
import chat.cyber.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWsController {

    public ChatWsController(ChatService chatService) {
        this.chatService = chatService;
    }

    private ChatService chatService;

    @MessageMapping("/send") // /app/send
    @SendTo("/topic/messages") // broadcast para todos inscritos em /topic/messages
    public ChatMessageDTO sendMessage(ChatMessageDTO message){

        return message;

    }
}
