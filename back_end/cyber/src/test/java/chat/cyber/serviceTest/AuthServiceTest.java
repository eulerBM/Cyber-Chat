package chat.cyber.serviceTest;


import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.AuthService;
import chat.cyber.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        jwtService = mock(JwtService.class);
        authService = new AuthService(userRepository, jwtService);

    }

    @Test
    @DisplayName("Testa se as senhas não são iguais")
    void shouldReturnErrorIfPasswordsAreDifferent(){

        ResponseEntity<?> response = authService.createUser(new CreateUserDTO("testName",
                "test@gmail.com", "123456789", "12345"));

        assertEquals(400, response.getStatusCode().value());


    }

    @Test
    @DisplayName("Testa se tem dois e-mails iguais salvos")
    void shouldReturnAnErrorIfTwoEmailsAreTheSameInTheDatabase(){

        CreateUserDTO dto = new CreateUserDTO("testName",
                "test@gmail.com", "123456789", "123456789");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(new User())); // simula e-mail já existente

        ResponseEntity<?> response = authService.createUser(dto);

        assertEquals(409, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar tudo OK")
    void shuldReturnAllOk(){

        CreateUserDTO dto = new CreateUserDTO("testName",
                "test@gmail.com", "123456789", "123456789");

        ResponseEntity<?> response = authService.createUser(dto);

        assertEquals(200, response.getStatusCode().value());
    }


}
