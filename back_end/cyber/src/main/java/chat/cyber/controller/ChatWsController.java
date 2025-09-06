package chat.cyber.controller;

import chat.cyber.controller.dtos.ChatMessageDTO;
import chat.cyber.entity.Chat;
import chat.cyber.entity.Message;
import chat.cyber.entity.User;
import chat.cyber.repository.ChatRepository;
import chat.cyber.repository.MessageRepository;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ChatWsController {

    public ChatWsController(ChatService chatService, SimpMessagingTemplate messagingTemplate,
                            ChatRepository chatRepository, UserRepository userRepository,
                            MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    @MessageMapping("/send") // /app/send
    public ChatMessageDTO sendMessage(ChatMessageDTO message){

        Chat chatEntity = chatRepository.findByIdPublic(message.getChatIdPublic())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat não encontrado"));

        User userEntity = userRepository.findByIdPublic(message.getSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User não encontrado"));

        Message messageEntity = new Message();
        messageEntity.setSender(userEntity);
        messageEntity.setContent(message.getContent());
        messageEntity.setTimestamp(message.getDate());
        messageEntity.setChat(chatEntity);

        messageRepository.save(messageEntity);

        messagingTemplate.convertAndSend("/queue/" + message.getChatIdPublic(), message);

        return message;

    }
}
