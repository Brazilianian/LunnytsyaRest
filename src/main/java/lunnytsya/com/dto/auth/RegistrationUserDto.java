package lunnytsya.com.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 24, message = "Довжина має бути більше за 2 символи, але менша ніж 24")
    private String username;

    // TODO: 29.01.2022 set min 8 
    @Size(min = 2, message = "Довжина паролю має бути більше за 8 символів")
    @NotNull
    @NotEmpty
    private String password;
}
