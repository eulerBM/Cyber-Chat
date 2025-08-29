package chat.cyber.controller;

import chat.cyber.controller.dtos.ChatMessageDTO;
import chat.cyber.entity.Chat;
import chat.cyber.entity.User;
import chat.cyber.repository.ChatRepository;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.ChatService;
import chat.cyber.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatWsController {

    public ChatWsController(ChatService chatService, SimpMessagingTemplate messagingTemplate, ChatRepository chatRepository, Message message, User user, UserRepository userRepository) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.chatRepository = chatRepository;
        this.message = message;

        this.userRepository = userRepository;
    }

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;
    private final Message message;
    private final UserRepository userRepository;

    @MessageMapping("/send") // /app/send
    public ChatMessageDTO sendMessage(ChatMessageDTO message){

        Optional<Chat> chatGetByIdPublic = chatRepository.findByIdPublic(message.getChatIdPublic());
        Optional<User> userGetBy = userRepository.findByIdPublic(message.);


        Chat getChat = chatGetByIdPublic.get();





        getChat.setMessages();

        List<Message> message = new ArrayList<>();

        Message msgText = new Message();



        getChat.setMessages();





        messagingTemplate.convertAndSend("/queue/" + message.getChatIdPublic(), message);

        System.out.println("to enviando kkkk "+ message);

        return message;

    }
}
