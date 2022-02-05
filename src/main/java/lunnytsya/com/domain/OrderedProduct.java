package lunnytsya.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lunnytsya.com.domain.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProduct extends BaseEntity{
    @OneToOne(cascade = CascadeType.MERGE)
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Positive(message="Кількість має буди додатнім числом")
    private int count;

    private double getTotalPrice() {
        return product.getPrice() * count;
    }
}
