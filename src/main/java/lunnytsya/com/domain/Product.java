package lunnytsya.com.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Length(message = "Кількість символів не має перевищувати за 64", max = 64)
    @NotEmpty(message = "Ім'я не має бути пустим")
    private String name;

    @Positive(message = "Ціна має бути більше за 0")
    private double price;

    @Length(message = "Кулькість символів не має перевищувати 2048", max = 2048)
    @NotEmpty(message = "Заповніть опис продукту")
    private String description;

    @Lob
    @NotEmpty(message = "Виберіть зображення")
    private String image;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
