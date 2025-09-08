package chat.cyber.controller;

import chat.cyber.controller.dtos.userOnlineDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserWsController {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public UserWsController(UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/this/online")
    public void thisUserOnine(userOnlineDTO data){
        Map<String, Object> payload = new HashMap<>();

        User getUser = userRepository.findByIdPublic(data.getUuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User não encontrado"));

        if (getUser.isOnline()){
            payload.put("status", true);

            messagingTemplate.convertAndSend("/queue/online/this/" + data.getUuidUser(), payload);

        } else {
            payload.put("status", false);

            messagingTemplate.convertAndSend("/queue/online/this/" + data.getUuidUser(), payload);
        }
    }

    @MessageMapping("/user/online")
    public boolean userOnine(userOnlineDTO data){

        User getUser = userRepository.findByIdPublic(data.getUuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User não encontrado"));

        if (getUser.isOnline()){

            messagingTemplate.convertAndSend("/queue/online/" + data.getUuidUser(), true);
            return true;

        }
        getUser.setOnline(true);

        userRepository.save(getUser);

        messagingTemplate.convertAndSend("/queue/online/" + data.getUuidUser(), true);

        return true;

    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event){
        Principal p = event.getUser();

        if (p != null){
            String userEmail = p.getName();
            User user = userRepository.findByEmail(userEmail).orElse(null);

            if (user != null){
                user.setOnline(false);
                userRepository.save(user);
                System.out.println("Usuario: "+user.getName() + " Desconetado");
                messagingTemplate.convertAndSend("/queue/online/" + user.getIdPublic(), false);

            }
        }
    }
}
