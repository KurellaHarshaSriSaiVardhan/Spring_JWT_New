package JWT_9.example.JWT;

import JWT_9.example.JWT.Repository.UserReposirtory;
import JWT_9.example.JWT.entities.ROLE;
import JWT_9.example.JWT.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//public class JwtApplication implements CommandLineRunner {
public class JwtApplication{
    @Autowired
    private UserReposirtory userReposirtory;

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

//    public void run(String... args){
//        User adminAccount =userReposirtory.findByRole(ROLE.ADMIN);
//        if(null==adminAccount){
//            User user =new User();
//            user.setEmail("admin@gmail.com");
//            user.setFirstname("admin");
//            user.setRole(ROLE.ADMIN);
//            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//            userReposirtory.save(user);
//        }
//    }

}
