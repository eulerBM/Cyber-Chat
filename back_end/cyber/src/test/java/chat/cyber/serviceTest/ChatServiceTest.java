package chat.cyber.serviceTest;

import chat.cyber.entity.User;
import chat.cyber.repository.ChatRepository;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.AuthService;
import chat.cyber.service.ChatService;
import chat.cyber.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.mock;

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
    @DisplayName("Deve retornar erro por não encontrar Emails")
    void MustReturnErrorForNotFindEmails(){

        User userGet

    }
}
