package chat.cyber.controllerTest;

import chat.cyber.controller.AuthController;
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
import static org.mockito.Mockito.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private AuthController authController;
    private UserRepository userRepository;
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        jwtService = mock(JwtService.class);
        AuthService authService = new AuthService(userRepository, jwtService);
        authController = new AuthController(authService);
    }

    @Test
    @DisplayName("Senhas diferentes devem retornar 400")
    void shouldReturnNotAcceptable_WhenPasswordsDoNotMatch(){
        CreateUserDTO dto = new CreateUserDTO("nameTest", "test@gmail.com", "12345", "123456");

        ResponseEntity<?> response = authController.CreateUser(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar 409 se e-mail já existir")
    void shouldReturnConflict_WhenEmailAlreadyExists(){
        CreateUserDTO dto = new CreateUserDTO("namTest", "test@gmail.com", "12345","12345");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(new User())); // email já existe

        ResponseEntity<?> response = authController.CreateUser(dto);

        assertEquals(409, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve salvar novo usuário se dados forem válidos")
    void shouldSaveUser_WhenValidData(){
        CreateUserDTO dto = new CreateUserDTO("namTest", "test@gmail.com", "12345","12345");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty()); // email não existe

        ResponseEntity<?> response = authController.CreateUser(dto);

        assertEquals(200, response.getStatusCode().value());
        verify(userRepository, times(1)).save(any(User.class)); // garante que salvou
    }
}
