package lunnytsya.com.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
    ENABLED("Активний"), DISABLED("Не активний"), DELETED("Видалений");

    private final String name;

    Status(String name) {
        this.name = name;
    }
}
