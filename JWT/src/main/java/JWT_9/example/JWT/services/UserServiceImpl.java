package JWT_9.example.JWT.services;

import JWT_9.example.JWT.Repository.UserReposirtory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserReposirtory userReposirtory;
    public UserServiceImpl(UserReposirtory userReposirtory) {
        this.userReposirtory = userReposirtory;
    }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username){
                return userReposirtory.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
