package chat.cyber.service;

import chat.cyber.controller.dtos.EmailUserDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.response.ErroResponse;
import chat.cyber.service.response.SearchUserResponse;
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

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErroResponse("Usuario com esse e-mail não existe", 404));

        }

        User userGet = getEmail.get();

        return ResponseEntity.ok().body(new SearchUserResponse(userGet.getName() ,userGet.getEmail(), userGet.getIdPublic()));
    }
}
