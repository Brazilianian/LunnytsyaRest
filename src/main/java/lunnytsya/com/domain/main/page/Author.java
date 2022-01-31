package lunnytsya.com.domain.main.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lunnytsya.com.domain.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity {

    @Length(message = "Кількість символів не має переважати за 2048", max = 2048)
    private String description;

    @Lob
    private String image;
}
