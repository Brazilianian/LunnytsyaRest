package lunnytsya.com.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationUserDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 24, message = "The length of the username must be above than 2 but less than 24")
    private String username;

    @Size(min = 2, message = "The length of the password must be above than 8")
    @NotNull
    @NotEmpty
    private String password;

    public RegistrationUserDto() {
    }

    public RegistrationUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
