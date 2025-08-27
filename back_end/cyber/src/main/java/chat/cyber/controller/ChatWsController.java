package chat.cyber.controller;

import chat.cyber.controller.dtos.ChatMessageDTO;
import chat.cyber.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWsController {

    public ChatWsController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    private ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send") // /app/send
    public ChatMessageDTO sendMessage(ChatMessageDTO message){

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), // ID do usuário destino
                "/queue/messages",       // fila privada
                message
        );

        System.out.println("to enviando kkkk "+ message);

        return message;

    }
}
