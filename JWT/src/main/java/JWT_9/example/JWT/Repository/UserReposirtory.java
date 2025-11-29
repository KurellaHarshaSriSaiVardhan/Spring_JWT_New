package JWT_9.example.JWT.Repository;

import JWT_9.example.JWT.entities.ROLE;
import JWT_9.example.JWT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposirtory extends JpaRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);

    User findByRole(ROLE role);

}
