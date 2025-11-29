package JWT_9.example.JWT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    public UserController() {

    }

    @GetMapping
    public ResponseEntity<String> greet(){
        return ResponseEntity.ok("USER");
    }
}
