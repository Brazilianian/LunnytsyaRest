package lunnytsya.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Length(message = "The size should be less than 255", max = 255)
    @NotEmpty(message = "The name should not be empty")
    private String name;

    @Positive(message = "The price must be above zero")
    private double price;

    @Length(message = "The length must be less than 2048", max = 2048)
    @NotEmpty(message = "The description should not be empty")
    private String description;

    @Lob
    @NotEmpty(message = "The image should not be empty")
    private String image;
}
