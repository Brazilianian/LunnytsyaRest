package lunnytsya.com.controller;

import lunnytsya.com.domain.User;
import lunnytsya.com.dto.AuthenticationRequestDto;
import lunnytsya.com.dto.AuthenticationResponseDto;
import lunnytsya.com.jwt.JwtUtility;
import lunnytsya.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user, BindingResult  bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.unprocessableEntity().body(ControllerUtils.getErrors(bindingResult));
        }

        User userRes = userService.registerUser(user);

        if (userRes == null) {
            Map<String, String> errorMessages = new HashMap<>();
            errorMessages.put("username", "the user with this username already exists");
            return ResponseEntity.unprocessableEntity().body(errorMessages);
        }

        return ResponseEntity.ok(userRes);
    }

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
