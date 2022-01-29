package lunnytsya.com.controller.auth;

import io.jsonwebtoken.impl.DefaultClaims;
import lunnytsya.com.controller.util.ControllerUtils;
import lunnytsya.com.domain.User;
import lunnytsya.com.dto.AuthenticationRequestDto;
import lunnytsya.com.dto.AuthenticationResponseDto;
import lunnytsya.com.dto.RegistrationUserDto;
import lunnytsya.com.jwt.JwtUtility;
import lunnytsya.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final JwtUtility jwtUtility;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public AuthRestController(JwtUtility jwtUtility, AuthenticationManager authenticationManager,
                              UserService userService) {
        this.jwtUtility = jwtUtility;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationUserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.unprocessableEntity().body(ControllerUtils.getErrors(bindingResult));
        }

        User user;
        try {
            user = userService.registerUser(userDto);
        } catch (EntityExistsException e) {
            Map<String, String> errorMessages = new HashMap<>();
            errorMessages.put("username", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(errorMessages);
        }

        return ResponseEntity.ok(user);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public AuthenticationResponseDto auth(@RequestBody AuthenticationRequestDto request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());

        final String token = jwtUtility.generateToken(userDetails);

        return new AuthenticationResponseDto(token);
    }
}
