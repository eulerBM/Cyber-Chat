package chat.cyber.service;

import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createUser(CreateUserDTO data){

        if (!data.password().equals(data.passwordConfirm())){

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Senhas Incompatíveis");

        }

        Optional<User> userEmail = userRepository.findByEmail(data.email());

        if (userEmail.isPresent()){

            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail ja cadastrado");
        }

        User user = new User(data);

        userRepository.save(user);

        return ResponseEntity.ok().build();

    }
}
