package chat.cyber.controllerTest;

import chat.cyber.controller.AuthController;
import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.AuthService;
import chat.cyber.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthControllerTest {

    private AuthController authController;
    private UserRepository userRepository;
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        AuthService authService = new AuthService(userRepository, jwtService);
        authController = new AuthController(authService);
    }

    @Test
    @DisplayName("Senhas não parecidas devem não passar")
    void shouldReturnNotAcceptable_WhenPasswordsDoNotMatch(){
        CreateUserDTO createUserDTO = new CreateUserDTO("nameTest", "test@gmail.com", "12345", "123456");

        ResponseEntity<?> response = authController.CreateUser(createUserDTO);

        assertEquals(406, response.getStatusCode().value());

    }

    @Test
    @DisplayName("Deve não salavr e-mail ja existente")
    void houldCheckIfTheEmailDoesNotSaveTwoIdenticalOnes(){
        CreateUserDTO createUserDTO = new CreateUserDTO("namTest", "test@gmail.com", "12345","12345");
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.CreateUser(createUserDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());

    }
}
