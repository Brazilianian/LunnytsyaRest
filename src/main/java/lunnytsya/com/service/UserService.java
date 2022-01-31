package lunnytsya.com.service;

import lombok.extern.slf4j.Slf4j;
import lunnytsya.com.domain.enums.Role;
import lunnytsya.com.domain.User;
import lunnytsya.com.domain.enums.Status;
import lunnytsya.com.dto.ProfileDto;
import lunnytsya.com.dto.RegistrationUserDto;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.jwt.JwtUtility;
import lunnytsya.com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.time.LocalDate;
import java.util.Collections;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final JwtUtility jwtUtility;

    @Autowired
    public UserService(UserRepo userRepo, JwtUtility jwtUtility) {
        this.userRepo = userRepo;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public User registerUser(RegistrationUserDto userDto){

        if (userRepo.existsByUsername(userDto.getUsername())) {
            throw new EntityExistsException("Користувач з таким імене вже існує");
        }

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        user.setRoles(Collections.singletonList(Role.USER));
        user.setStatus(Status.ENABLED);

        user.setCreated(LocalDate.now());
        user.setUpdated(LocalDate.now());

        user = userRepo.save(user);
        log.info("User with username " + userDto.getUsername() + " was successfully registered");
        return user;
    }

    public User getUser(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            String username = jwtUtility.getUsernameFromToken(token);
            return userRepo.findByUsername(username);
        }
        throw new HeadlessException("Uncorrected token");
    }

    public User updateUser(ProfileDto profileDto) {

        if (!userRepo.existsByUsername(profileDto.getUsername())) {
            log.warn("Couldn`t update profile of user with username '" + profileDto.getUsername());
            throw new EntityExistsException("Користувача з іменем '" + profileDto.getUsername() + "' не існує");
        }

        User user = userRepo.findByUsername(profileDto.getUsername());

        user.setName(profileDto.getName());
        user.setSurname(profileDto.getSurname());
        user.setEmail(profileDto.getEmail());
        user.setImage(profileDto.getImage());

        user.setUpdated(LocalDate.now());
        return userRepo.save(user);
    }
}
