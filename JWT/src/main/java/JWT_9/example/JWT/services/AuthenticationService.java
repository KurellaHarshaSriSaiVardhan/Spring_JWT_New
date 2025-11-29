package JWT_9.example.JWT.services;

import JWT_9.example.JWT.dto.JWTAuthencticationResponse;
import JWT_9.example.JWT.dto.RefreshTokenRequest;
import JWT_9.example.JWT.dto.SignInRequest;
import JWT_9.example.JWT.dto.SignUpRequest;
import JWT_9.example.JWT.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JWTAuthencticationResponse signin(SignInRequest signInRequest);

    JWTAuthencticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
