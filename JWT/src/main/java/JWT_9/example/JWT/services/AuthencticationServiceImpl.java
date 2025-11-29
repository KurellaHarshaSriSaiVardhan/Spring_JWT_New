package JWT_9.example.JWT.services;

import JWT_9.example.JWT.Repository.UserReposirtory;
import JWT_9.example.JWT.dto.JWTAuthencticationResponse;
import JWT_9.example.JWT.dto.RefreshTokenRequest;
import JWT_9.example.JWT.dto.SignInRequest;
import JWT_9.example.JWT.dto.SignUpRequest;
import JWT_9.example.JWT.entities.ROLE;
import JWT_9.example.JWT.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthencticationServiceImpl implements AuthenticationService {

    private final UserReposirtory  userReposirtory;
    private final PasswordEncoder passwordEncoder;

    public AuthencticationServiceImpl(UserReposirtory userReposirtory, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userReposirtory = userReposirtory;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
        user.setRole(ROLE.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userReposirtory.save(user);
    }

    public JWTAuthencticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));
        var user = userReposirtory.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JWTAuthencticationResponse jwtAuthencticationResponse = new JWTAuthencticationResponse();
        jwtAuthencticationResponse.setToken(jwt);
        jwtAuthencticationResponse.setRefreshToken(refreshToken);
        return jwtAuthencticationResponse;
    }

    public JWTAuthencticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
        User user=userReposirtory.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt=jwtService.generateToken(user);
            JWTAuthencticationResponse jwtAuthencticationResponse = new JWTAuthencticationResponse();
            jwtAuthencticationResponse.setToken(jwt);
            jwtAuthencticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthencticationResponse;
        }
        return null;
    }

}
