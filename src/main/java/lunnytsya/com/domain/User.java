package lunnytsya.com.domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Size(min = 2, max = 24, message = "The length of the name must be above than 2 but less than 24")
    private String name;

    @Size(min = 2, max = 24, message = "The length of the surname must be above than 2 but less than 24")
    private String surname;

    @Size(min = 2, max = 24, message = "The length of the username must be above than 2 but less than 24")
    private String username;

    @Size(min = 8, message = "The length of the password must be above than 8")
    private String password;

    @Email
    private String email;

    private boolean isActive;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Long id, LocalDate created, LocalDate updated, String name, String surname,
                List<Role> roles, String username, String password, String email, boolean isActive) {
        super(id, created, updated);
        this.name = name;
        this.surname = surname;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
