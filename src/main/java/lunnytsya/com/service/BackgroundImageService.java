package lunnytsya.com.service;

import lombok.extern.slf4j.Slf4j;
import lunnytsya.com.domain.enums.Status;
import lunnytsya.com.domain.main.page.BackgroundImage;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.BackgroundImageRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class BackgroundImageService implements IService<BackgroundImage> {

    private final BackgroundImageRepo backgroundImageRepo;

    public BackgroundImageService(BackgroundImageRepo backgroundImageRepo) {
        this.backgroundImageRepo = backgroundImageRepo;
    }

    public List<BackgroundImage> findAll() {
        return backgroundImageRepo.findAll();
    }

    @Override
    public BackgroundImage save(BackgroundImage backgroundImage) {
        backgroundImage.setCreated(LocalDateTime.now());
        backgroundImage.setUpdated(LocalDateTime.now());
        backgroundImage = backgroundImageRepo.save(backgroundImage);
        log.info("The background image was saved");
        return backgroundImage;
    }

    @Override
    public BackgroundImage delete(Long backgroundImageId) {
        if (backgroundImageRepo.existsById(backgroundImageId)) {
            log.warn(String.format("The background image with id '%d' was not delete - there is no one background image with the same id", backgroundImageId));
            throw new EntityExistsException(String.format("Фонового зображення з id '%d' не існує", backgroundImageId));
        }

        BackgroundImage backgroundImage = backgroundImageRepo.getById(backgroundImageId);
        backgroundImage.setStatus(Status.DELETED);
        backgroundImage.setUpdated(LocalDateTime.now());
        backgroundImage = backgroundImageRepo.save(backgroundImage);
        log.info(String.format("The background image with id '%d' was deleted", backgroundImageId));
        return backgroundImage;
    }

    @Override
    public BackgroundImage update(BackgroundImage backgroundImage) {
        if (backgroundImageRepo.existsById(backgroundImage.getId())) {
            log.warn(String.format("The background image with id '%d' was not updated - there is no one background image with the same id", backgroundImage.getId()));
            throw new EntityExistsException(String.format("Фонового зображення з id '%d' не існує", backgroundImage.getId()));
        }

        BackgroundImage backgroundImageDb = backgroundImageRepo.getById(backgroundImage.getId());
        backgroundImage.setCreated(backgroundImageDb.getCreated());
        backgroundImage.setUpdated(LocalDateTime.now());
        backgroundImage = backgroundImageRepo.save(backgroundImage);
        log.info(String.format("The background image with id '%d' was updated", backgroundImage.getId()));
        return backgroundImage;
    }
}
