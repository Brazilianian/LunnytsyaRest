package lunnytsya.com.repository;

import lunnytsya.com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

}
