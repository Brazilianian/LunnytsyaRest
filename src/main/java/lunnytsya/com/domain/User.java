package lunnytsya.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lunnytsya.com.domain.enums.Role;
import lunnytsya.com.domain.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Size(min = 2, max = 24, message = "Кількість символів має бути більше за 2, але менше ніж 24")
    private String name;

    @Size(min = 2, max = 24, message = "Кількість символів має бути більше за 2, але менше ніж 24")
    private String surname;

    @Size(min = 2, max = 24, message = "Кількість символів має бути більше за 2, але менше ніж 24")
    private String username;

    // TODO: 31.01.2022 set min 8
    @Size(min = 2, message = "Пароль має складатися більше ніж з 8 символів")
    private String password;

    @Email
    private String email;

    @Lob
    private String image;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus().equals(Status.ENABLED);
    }

    public String getName() {
        return name;
    }
}
