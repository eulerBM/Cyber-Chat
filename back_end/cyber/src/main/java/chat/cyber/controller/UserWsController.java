package chat.cyber.controller;

import chat.cyber.controller.dtos.userOnlineDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserWsController {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public UserWsController(UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/user/online")
    public boolean userOnine(userOnlineDTO data){

        User getUser = userRepository.findByIdPublic(data.getUuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User não encontrado"));


        getUser.setOnline(true);

        userRepository.save(getUser);

        messagingTemplate.convertAndSend("/queue/online/" + data.getUuidUser(), true);
        System.out.println("To enviando :D");

        return true;

    }
}
