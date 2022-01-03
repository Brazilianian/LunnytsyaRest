package lunnytsya.com.service;

import lunnytsya.com.domain.main.page.Author;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.AuthorRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IService<Author> {

    final static Logger logger = Logger.getLogger(String.valueOf(BackgroundImageService.class));

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
    public void save(Author author) {
        try {
            authorRepo.deleteAll();
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        authorRepo.save(author);
        logger.info("The author was saved");
    }

    @Override
    public void delete(Author author) {
        logger.warn("NOT IMPLEMENT");
    }

    @Override
    public void update(Author author) {
        logger.warn("NOT IMPLEMENTS");
    }
}
