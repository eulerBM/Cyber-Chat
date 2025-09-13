package chat.cyber.serviceTest;

import chat.cyber.controller.dtos.CreateChatDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.ChatRepository;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ChatServiceTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    private ChatService chatService;

    @BeforeEach
    void setup() {
        chatService = new ChatService(chatRepository, userRepository);
    }

    /// createChat

    @Test
    @DisplayName("Deve retornar erro por não encontrar Emails -> EMPTY")
    void MustReturnErrorForNotFindEmails(){

        CreateChatDTO data = new CreateChatDTO(UUID.randomUUID(), UUID.randomUUID());

        Optional<User> userSendByIdpublic = userRepository.findByIdPublic(data.idPublicUserSend());
        Optional<User> userReceivedByIdpublic = userRepository.findByIdPublic(data.idPublicUserReceived());

        assertEquals(Optional.empty(), userSendByIdpublic);
        assertEquals(Optional.empty(), userReceivedByIdpublic);


    }

    @Test
    @DisplayName("Deve retornar sucesso ao buscar os Users -> TRUE")
    void MustReturnSuccessWhenSearchingUsers(){

        UUID uuidUser = UUID.randomUUID();

        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();

        User createUser = new User();
        createUser.setIdPublic(uuidUser);
        createUser.setName("teste");
        createUser.setEmail("testemail@gmail.com");
        createUser.setOnline(false);
        createUser.setPassword(encode.encode("teste123456789"));

        userRepository.save(createUser);

        CreateChatDTO data = new CreateChatDTO(uuidUser, uuidUser);

        Optional<User> userSendByIdpublic = userRepository.findByIdPublic(data.idPublicUserSend());
        Optional<User> userReceivedByIdpublic = userRepository.findByIdPublic(data.idPublicUserReceived());

        assertTrue(userSendByIdpublic.isPresent());
        assertTrue(userReceivedByIdpublic.isPresent());
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar os Users -> 404")
    void ShouldReturnHttpErrorWhenUnableToFindUsers(){

        CreateChatDTO data = new CreateChatDTO(UUID.randomUUID(), UUID.randomUUID());

        ResponseEntity<?> response = chatService.createChat(data);

        assertEquals(404, response.getStatusCode().value());

    }

    @Test
    @DisplayName("Deve retornar sucesso ao cria os chats -> OK ")
    void MustReturnSuccessWhenSearchingUser(){

        UUID uuidUser1 = UUID.randomUUID();
        UUID uuidUser2 = UUID.randomUUID();

        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();

        User createUser1 = new User();
        createUser1.setIdPublic(uuidUser1);
        createUser1.setName("teste1");
        createUser1.setEmail("testemail1@gmail.com");
        createUser1.setOnline(false);
        createUser1.setPassword(encode.encode("teste1_123456789"));

        User createUser2 = new User();
        createUser2.setIdPublic(uuidUser2);
        createUser2.setName("teste2");
        createUser2.setEmail("testemail2@gmail.com");
        createUser2.setOnline(false);
        createUser2.setPassword(encode.encode("teste2_123456789"));

        userRepository.save(createUser1);
        userRepository.save(createUser2);

        CreateChatDTO createChatDTO = new CreateChatDTO(uuidUser1, uuidUser2);

        ResponseEntity<?> response = chatService.createChat(createChatDTO);

        assertEquals(200, response.getStatusCode().value());

    }




}
