package lunnytsya.com.controller;

import lunnytsya.com.domain.BackgroundImage;
import lunnytsya.com.service.BackgroundImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/background-image")
public class BackgroundImageController {

    private final BackgroundImageService backgroundImageService;


    public BackgroundImageController(BackgroundImageService backgroundImageService) {
        this.backgroundImageService = backgroundImageService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-all")
    public ResponseEntity<List<BackgroundImage>> getAll() {
        List<BackgroundImage> backgroundImages = backgroundImageService.findAll();
        return new ResponseEntity<>(backgroundImages, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/create")
    public ResponseEntity<BackgroundImage> create(@RequestBody BackgroundImage backgroundImage) {
        if (backgroundImage == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            backgroundImageService.save(backgroundImage);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(backgroundImage, HttpStatus.OK);
    }

}
