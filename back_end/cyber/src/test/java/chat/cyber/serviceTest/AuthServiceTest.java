package chat.cyber.serviceTest;


import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.AuthService;
import chat.cyber.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@DataJpaTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    private AuthService authService;

    private JwtService jwtService;


    @BeforeEach
    void setup() {
        jwtService = mock(JwtService.class);
        authService = new AuthService(userRepository, jwtService);
    }

    /// CreateUser Service

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

        // Primeiro cadastro
        ResponseEntity<?> responseFirst = authService.createUser(dto);
        assertEquals(200, responseFirst.getStatusCode().value());

        // Segundo cadastro → deve dar erro de duplicado
        ResponseEntity<?> responseEqual = authService.createUser(dto);
        assertEquals(409, responseEqual.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar tudo OK")
    void shuldReturnAllOk(){

        CreateUserDTO dto = new CreateUserDTO("testName",
                "test@gmail.com", "123456789", "123456789");

        ResponseEntity<?> response = authService.createUser(dto);

        assertEquals(200, response.getStatusCode().value());
    }

    /// LoginService




}
