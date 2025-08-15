package chat.cyber.controller;

import chat.cyber.controller.dtos.CreateUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
public class auth {

    public ResponseEntity<?> CreateUser(@RequestBody CreateUserDTO data){



    }
}
