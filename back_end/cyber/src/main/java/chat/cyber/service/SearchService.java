package chat.cyber.service;

import chat.cyber.controller.dtos.EmailUserDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchService {

    public SearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    public ResponseEntity<?> searchByEmail(EmailUserDTO data){

        Optional<User> getEmail = userRepository.findByEmail(data.email());

        if (getEmail.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não existe");

        }

        return ResponseEntity.ok().body(getEmail.get());
    }
}
