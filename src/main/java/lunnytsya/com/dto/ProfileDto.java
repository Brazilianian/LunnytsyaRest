package lunnytsya.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String image;
    private String name;
    private String surname;

    @Email(message = "Невірна адреса електронної скриньки")
    private String email;

    @Size(min = 2, max = 24, message = "Кількість символів має бути більше за 2, але менше ніж 24")
    private String username;

    @Override
    public String toString() {
        return "ProfileDto{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
