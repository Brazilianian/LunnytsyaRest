package lunnytsya.com.service;

import lombok.extern.slf4j.Slf4j;
import lunnytsya.com.domain.Product;
import lunnytsya.com.domain.enums.Status;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.ProductRepo;
import org.hibernate.mapping.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
public class ProductService implements IService<Product> {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product save(Product product) {
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());
        product.setStatus(Status.ENABLED);

        if (!productRepo.existsByName(product.getName())) {
            Product productDb = productRepo.save(product);
            log.info(String.format("The product with name '%s' was saved", productDb.getName()));
            return productDb;
        }

        throw new EntityExistsException();
    }

    @Override
    public Product delete(Long productId) {
        Product product = null;
        if (!productRepo.existsById(productId)) {
            log.warn(String.format("The product with id '%d' was not delete - there is no one product with id '%d'", productId, productId));
            throw new EntityExistsException(String.format("Продукту з id '%d' не існує", productId));
        }

        product = productRepo.getById(productId);
        product.setStatus(Status.DELETED);
        product.setUpdated(LocalDateTime.now());
        Product productDb = productRepo.save(product);

        log.info(String.format("The product with id '%d' was deleted", productId));
        return productDb;
    }

    @Override
    public Product update(Product product) {
        if (!productRepo.existsById(product.getId())) {
            log.warn(String.format("The product with name '%s' was not updated - there is no one product with id '%d'", product.getName(), product.getId()));
            throw new EntityExistsException(String.format("Продукту з іменем '%s' не існує", product.getName()));
        }

        if (productRepo.existsByName(product.getName())) {
            throw new EntityExistsException("Вже існує продукт з таким іменем");
        }

        Product productDb = productRepo.findById(product.getId()).get();
        if (Objects.equals(productDb.getName(), product.getName())) {
            return null;
        }
        product.setCreated(productDb.getCreated());
        product.setUpdated(LocalDateTime.now());

        productDb = productRepo.save(product);
        log.info(String.format("The product with name '%s' was updated", product.getName()));
        return productDb;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public Product getById(Long productId) {
        if (!productRepo.existsById(productId)) {
            throw new EntityExistsException(String.format("Продукт з id '%d' не існує", productId));
        }
        return productRepo.findById(productId).orElse(null);
    }
}
