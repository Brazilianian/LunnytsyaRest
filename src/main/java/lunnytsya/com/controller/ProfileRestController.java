package lunnytsya.com.controller;

import lunnytsya.com.controller.util.ControllerUtils;
import lunnytsya.com.domain.User;
import lunnytsya.com.dto.ProfileDto;
import lunnytsya.com.jwt.JwtUtility;
import lunnytsya.com.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileRestController {

    private final UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        User user;
        try {
            user = userService.getUser(request);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody @Valid ProfileDto profileDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        User user;
        try {
            user = userService.updateUser(profileDto);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }
}
