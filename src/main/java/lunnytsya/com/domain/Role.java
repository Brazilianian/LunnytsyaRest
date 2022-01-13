package lunnytsya.com.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("Користувач"), ADMIN("Адміністратор");

    final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
