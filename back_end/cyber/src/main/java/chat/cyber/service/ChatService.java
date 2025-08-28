package chat.cyber.service;

import chat.cyber.controller.dtos.CreateChatDTO;
import chat.cyber.entity.Chat;
import chat.cyber.entity.User;
import chat.cyber.repository.ChatRepository;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.response.ChatIdPublicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {


    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;


    public ResponseEntity<?> createChat(CreateChatDTO data){

        Optional<User> userSendByIdpublic = userRepository.findByIdPublic(data.idPublicUserSend());
        Optional<User> userReceivedByIdpublic = userRepository.findByIdPublic(data.idPublicUserReceived());

        if (userSendByIdpublic.isEmpty() || userReceivedByIdpublic.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuarios não encontrados");
        }

        User userSendGet = userSendByIdpublic.get();
        User userReceivedGet = userReceivedByIdpublic.get();

        Set<User> usersSet = new HashSet<>();

        usersSet.add(userSendGet);
        usersSet.add(userReceivedGet);

        Chat createChat = new Chat("chat: " + userSendGet.getEmail() + userReceivedGet.getEmail(), usersSet);

        chatRepository.save(createChat);

        return ResponseEntity.status(HttpStatus.OK).body(new ChatIdPublicResponse(createChat.getIdPublic()));

    }
}
