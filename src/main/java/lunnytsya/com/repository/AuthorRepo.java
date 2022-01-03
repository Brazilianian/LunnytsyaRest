package lunnytsya.com.repository;

import lunnytsya.com.domain.main.page.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
}
