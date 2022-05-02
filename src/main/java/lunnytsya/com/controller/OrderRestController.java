package lunnytsya.com.controller;

import lunnytsya.com.controller.util.ControllerUtils;
import lunnytsya.com.domain.Order;
import lunnytsya.com.domain.User;
import lunnytsya.com.dto.order.OrderDto;
import lunnytsya.com.service.OrderService;
import lunnytsya.com.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderRestController {

    private final UserService userService;
    private final OrderService orderService;

    public OrderRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(HttpServletRequest request,
                                       @RequestBody @Valid OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = userService.getUser(request);

        Order order = orderService.fillOrder(orderDto, user);

        orderService.save(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
