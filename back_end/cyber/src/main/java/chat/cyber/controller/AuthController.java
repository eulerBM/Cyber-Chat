package chat.cyber.controller;

import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.controller.dtos.LoginUserDTO;
import chat.cyber.controller.dtos.RefreshTokenDTO;
import chat.cyber.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/users/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> CreateUser(@Valid @RequestBody CreateUserDTO data){

        return authService.createUser(data);

    }

    @PostMapping(path = "login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> LoginUser(@Valid @RequestBody LoginUserDTO data){

        return authService.loginUser(data);

    }

    @PostMapping(path = "refresh", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenDTO data){

        return authService.refresToken(data);

    }
}
