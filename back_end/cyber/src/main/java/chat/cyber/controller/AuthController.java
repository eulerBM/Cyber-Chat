package chat.cyber.controller;

import chat.cyber.controller.dtos.CreateUserDTO;
import chat.cyber.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> CreateUser(@RequestBody CreateUserDTO data){

    return authService.createUser(data);

    }
}
