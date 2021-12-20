package lunnytsya.com.service;

import lunnytsya.com.domain.BackgroundImage;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.BackgroundImageRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackgroundImageService implements IService<BackgroundImage> {

    final static Logger logger = Logger.getLogger(String.valueOf(BackgroundImageService.class));

    private final BackgroundImageRepo backgroundImageRepo;

    public BackgroundImageService(BackgroundImageRepo backgroundImageRepo) {
        this.backgroundImageRepo = backgroundImageRepo;
    }

    public List<BackgroundImage> findAll() {
        return backgroundImageRepo.findAll();
    }

    @Override
    public void save(BackgroundImage backgroundImage) {
        backgroundImageRepo.save(backgroundImage);
        logger.info("The background image was saved");
    }

    @Override
    public void delete(BackgroundImage backgroundImage) {
        if (backgroundImageRepo.existsById(backgroundImage.getId())) {
            backgroundImageRepo.deleteById(backgroundImage.getId());
            logger.info("The background image with id '" + backgroundImage.getId() + "' was deleted");
        } else {
            logger.warn("The background image with id '" + backgroundImage.getId() + "' was not delete - there is no one background image with the same id");
        }
    }

    @Override
    public void update(BackgroundImage backgroundImage) {
        if (backgroundImageRepo.existsById(backgroundImage.getId())) {
            backgroundImageRepo.save(backgroundImage);
            logger.info("The background image with id '" + backgroundImage.getId() + "' was updated");
        } else {
            logger.warn("The background image with id '" + backgroundImage.getId() + "' was not updated - there is no one background image with the same id");
        }
    }
}
