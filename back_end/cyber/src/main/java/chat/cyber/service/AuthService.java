package chat.cyber.service;

import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.controller.dtos.LoginUserDTO;
import chat.cyber.controller.dtos.RefreshTokenDTO;
import chat.cyber.entity.User;
import chat.cyber.repository.UserRepository;
import chat.cyber.service.response.ErroResponse;
import chat.cyber.service.response.LoginUserResponse;
import chat.cyber.service.response.UserInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> createUser(CreateUserDTO data){

        if (!data.password().equals(data.passwordConfirm())){

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Senhas Incompatíveis");

        }

        Optional<User> userEmail = userRepository.findByEmail(data.email());

        if (userEmail.isPresent()){

            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail ja cadastrado");
        }

        User user = new User(data.name(), data.email(), passwordEncoder.encode(data.password()));

        userRepository.save(user);

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<?> loginUser(LoginUserDTO data){

        Optional<User> userEmail = userRepository.findByEmail(data.email());

        if (userEmail.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErroResponse("E-mail não existe", HttpStatus.NOT_FOUND.value()));
        }

        User user = userEmail.get();

        boolean passwordIsEqual = passwordEncoder.matches(data.password(), user.getPassword());

        if (!passwordIsEqual){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErroResponse("Senhas diferentes", HttpStatus.UNAUTHORIZED.value()));

        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginUserResponse(accessToken, refreshToken,
                        new UserInfoResponse(user.getIdPublic(), user.getName(), user.getEmail())));

    }

    public ResponseEntity<?> refresToken(RefreshTokenDTO data){
        
    }
}
