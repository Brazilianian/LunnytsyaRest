package lunnytsya.com.controller;

import lunnytsya.com.domain.BackgroundImage;
import lunnytsya.com.service.BackgroundImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
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
    public ResponseEntity<MultipartFile> create(@RequestBody MultipartFile image) {
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            backgroundImageService.save(new BackgroundImage(Base64.getEncoder().encodeToString(image.getBytes())));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

}
