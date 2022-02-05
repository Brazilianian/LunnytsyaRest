package lunnytsya.com.service;

import lombok.extern.slf4j.Slf4j;
import lunnytsya.com.domain.main.page.Author;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.AuthorRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthorService implements IService<Author> {

    private final AuthorRepo authorRepo;

    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public Author get() {
        try {
            return authorRepo.findAll().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Author save(Author author) {
        author.setCreated(LocalDateTime.now());
        author.setUpdated(author.getUpdated());
        author = authorRepo.save(author);
        log.info("The author was saved");
        return author;
    }

    @Override
    public Author delete(Long authorId) {
        log.warn("NOT IMPLEMENT");
        return null;
    }

    @Override
    public Author update(Author author) {
        log.warn("NOT IMPLEMENTS");
        return null;
    }
}
