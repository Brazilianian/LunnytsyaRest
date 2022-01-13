package lunnytsya.com.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
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

    private boolean isVisible = true;

    public Product() {
    }

    public Product(Long id, LocalDate created, LocalDate updated, String name, double price, String description, String image, boolean isVisible) {
        super(id, created, updated);
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
