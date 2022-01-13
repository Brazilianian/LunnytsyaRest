package lunnytsya.com.domain.main.page;

import lunnytsya.com.domain.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
public class Author extends BaseEntity {

    @Length(message = "The length must me less than 2048", min = 0, max = 2048)
    @NotEmpty(message = "Description must not be empty")
    private String description;

    @Lob
    @NotEmpty(message = "Choose the image")
    private String image;

    public Author() {
    }

    public Author(Long id, LocalDate created, LocalDate updated, String description, String image) {
        super(id, created, updated);
        this.description = description;
        this.image = image;
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
}
