package lunnytsya.com.domain.main.page;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(message = "The length must me less than 2048", min = 0, max = 2048)
    @NotEmpty(message = "Description must not be empty")
    private String description;

    @Lob
    @NotEmpty(message = "Choose the image")
    private String image;

    public Author() {
    }

    public Author(String description, String image) {
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
