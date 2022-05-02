package lunnytsya.com.domain;

import lombok.*;
import lunnytsya.com.domain.enums.OrderStatus;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProduct extends BaseEntity{
    @OneToOne(cascade = CascadeType.MERGE)
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Positive
    private int count;

    private double getTotalPrice() {
        return product.getPrice() * count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderedProduct that = (OrderedProduct) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
