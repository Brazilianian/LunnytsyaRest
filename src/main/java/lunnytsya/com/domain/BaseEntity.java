package lunnytsya.com.domain;

import lombok.Data;
import lunnytsya.com.domain.enums.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate created;
    private LocalDate updated;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DISABLED;
}
