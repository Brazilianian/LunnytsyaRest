package lunnytsya.com.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProductDto {

    @Positive
    private int count;

    @NotNull
    private ProductForOrderDto product;
}
