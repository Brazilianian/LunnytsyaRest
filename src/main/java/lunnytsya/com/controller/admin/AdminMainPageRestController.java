package lunnytsya.com.controller.admin;

import lunnytsya.com.controller.util.ControllerUtils;
import lunnytsya.com.domain.main.page.Author;
import lunnytsya.com.domain.main.page.BackgroundImage;
import lunnytsya.com.service.AuthorService;
import lunnytsya.com.service.BackgroundImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/main-page")
public class AdminMainPageRestController {

    private final BackgroundImageService backgroundImageService;

    private final AuthorService authorService;

    public AdminMainPageRestController(BackgroundImageService backgroundImageService, AuthorService authorService) {
        this.backgroundImageService = backgroundImageService;
        this.authorService = authorService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/background-image")
    public ResponseEntity<BackgroundImage> create(@RequestBody BackgroundImage image) {
        if (image == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            image = backgroundImageService.save(image);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/author")
    public ResponseEntity<?> setAuthor(@RequestBody @Valid Author author,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            if (author == null) {
                return ResponseEntity.badRequest().body(null);
            }

            author = authorService.save(author);
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(author);
        }
    }
}
