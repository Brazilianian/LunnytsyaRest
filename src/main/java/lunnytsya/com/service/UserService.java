package lunnytsya.com.service;

import lunnytsya.com.domain.Role;
import lunnytsya.com.domain.User;
import lunnytsya.com.dto.RegistrationUserDto;
import lunnytsya.com.jwt.JwtUtility;
import lunnytsya.com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.xml.bind.ValidationException;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final JwtUtility jwtUtility;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, JwtUtility jwtUtility, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public User registerUser(RegistrationUserDto userDto){

        if (!userRepo.existsByUsername(userDto.getUsername())) {

            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setRoles(Collections.singletonList(Role.USER));
            user.setActive(true);

            return userRepo.save(user);
        } else {
            throw new EntityExistsException("Користувач з таким імене вже існує");
        }
    }

    public User getUser(String token) {
        String username = jwtUtility.getUsernameFromToken(token);
        return userRepo.findByUsername(username);
    }
}
