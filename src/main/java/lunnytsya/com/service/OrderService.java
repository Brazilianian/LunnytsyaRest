package lunnytsya.com.service;

import lombok.extern.slf4j.Slf4j;
import lunnytsya.com.domain.Order;
import lunnytsya.com.domain.OrderedProduct;
import lunnytsya.com.domain.User;
import lunnytsya.com.domain.enums.OrderStatus;
import lunnytsya.com.dto.order.OrderDto;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService implements IService<Order> {

    private final ProductRepo productRepo;

    public OrderService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public Order delete(Long id) {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    public Order fillOrder(OrderDto orderDto, User user) {
        Order order = new Order();

        List<OrderedProduct> orderedProducts = orderDto.getOrderedProductDtoList().stream().map(op -> {
            OrderedProduct orderedProduct = new OrderedProduct();

            orderedProduct.setProduct(productRepo.getById(op.getProduct().getId()));
            orderedProduct.setCount(op.getCount());
            orderedProduct.setOrderStatus(OrderStatus.ORDERED);

            return orderedProduct;
        }).collect(Collectors.toList());

        order.setUser(user);
        order.setOrderedProducts(orderedProducts);

        return order;
    }
}
