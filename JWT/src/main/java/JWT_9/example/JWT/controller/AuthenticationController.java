package JWT_9.example.JWT.controller;

import JWT_9.example.JWT.dto.JWTAuthencticationResponse;
import JWT_9.example.JWT.dto.RefreshTokenRequest;
import JWT_9.example.JWT.dto.SignInRequest;
import JWT_9.example.JWT.dto.SignUpRequest;
import JWT_9.example.JWT.entities.User;
import JWT_9.example.JWT.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
            return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthencticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthencticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
