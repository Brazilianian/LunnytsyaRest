package lunnytsya.com.domain.main.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lunnytsya.com.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackgroundImage extends BaseEntity {

    @Lob
    private String content;
}
