package lunnytsya.com.controller;

import lunnytsya.com.dto.AuthenticationRequestDto;
import lunnytsya.com.dto.AuthenticationResponseDto;
import lunnytsya.com.service.UserService;
import lunnytsya.com.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping
    public String home() {
        System.out.println("sadas");
        return "Welcome";
    }

    @PostMapping("/asd")
    public AuthenticationResponseDto auth(@RequestBody AuthenticationRequestDto request) throws Exception {
        System.out.println(request.getUsername());
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
