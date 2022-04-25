package lunnytsya.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Data
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price, description, image);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
