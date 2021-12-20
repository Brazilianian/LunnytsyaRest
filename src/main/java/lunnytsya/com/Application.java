package lunnytsya.com;

import lunnytsya.com.domain.Product;
import lunnytsya.com.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    static private ProductService productService;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        productService = new ProductService(null);
        productService.save(new Product());
    }
}
