package lunnytsya.com.controller;

import lunnytsya.com.domain.main.page.Author;
import lunnytsya.com.domain.main.page.BackgroundImage;
import lunnytsya.com.service.AuthorService;
import lunnytsya.com.service.BackgroundImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/main-page")
public class MainPageRestController {

    private final BackgroundImageService backgroundImageService;

    private final AuthorService authorService;

    public MainPageRestController(BackgroundImageService backgroundImageService, AuthorService authorService) {
        this.backgroundImageService = backgroundImageService;
        this.authorService = authorService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/background-image/get-all")
    public ResponseEntity<List<BackgroundImage>> getAll() {
        List<BackgroundImage> backgroundImages = backgroundImageService.findAll();
        return new ResponseEntity<>(backgroundImages, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/background-image/get-main")
    public ResponseEntity<BackgroundImage> getMain() {
        try {
            List<BackgroundImage> images = backgroundImageService.findAll();
            return ResponseEntity
                    .ok()
                    .body(images.get(images.size() - 1));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/background-image")
    public ResponseEntity<BackgroundImage> create(@RequestBody BackgroundImage image) {
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            backgroundImageService.save(image);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/author")
    public ResponseEntity<Author> getAuthor() {
        try {
            Author author = authorService.get();
            if (author == null) {
                return new ResponseEntity<>(new Author(), HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new Author());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/author")
    public ResponseEntity<?> setAuthor(@RequestBody @Valid Author author,
                                            BindingResult bindingResult) {
        try {
            if (author == null) {
                return ResponseEntity
                        .badRequest()
                        .body(null);
            }
            if (bindingResult.hasErrors()) {
                Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
                return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            authorService.save(author);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(author);
        }
    }
}
