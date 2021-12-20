package lunnytsya.com.repository;

import lunnytsya.com.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
