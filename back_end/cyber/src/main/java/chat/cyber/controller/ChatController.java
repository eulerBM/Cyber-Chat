package chat.cyber.controller;

import chat.cyber.controller.dtos.CreateChatDTO;
import chat.cyber.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/chat/")
public class ChatController {

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    private final ChatService chatService;

    @PostMapping(path = "create")
    public ResponseEntity<?> createChat(@Valid @RequestBody CreateChatDTO data){

        return chatService.createChat(data);

    }
}
