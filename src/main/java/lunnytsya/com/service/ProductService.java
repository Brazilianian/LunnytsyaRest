package lunnytsya.com.service;

import lombok.extern.slf4j.Slf4j;
import lunnytsya.com.domain.Product;
import lunnytsya.com.domain.enums.Status;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@Slf4j
@Service
public class ProductService implements IService<Product> {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product save(Product product) {

        product.setCreated(LocalDate.now());
        product.setUpdated(LocalDate.now());

        Product productDb = productRepo.save(product);
        log.info("The product with name '" + product.getName() + "' was saved");
        return productDb;
    }

    @Override
    public Product delete(Long productId) {
        Product product = null;
        if (!productRepo.existsById(productId)) {
            log.warn("The product with id '" + productId + "' was not delete - there is no one product with id " + productId);
            throw new EntityExistsException("Продукту з іменем '" + product.getName() + "' не існує");
        }

        product = productRepo.getById(productId);
        product.setStatus(Status.DELETED);
        product.setUpdated(LocalDate.now());
        Product productDb = productRepo.save(product);

        log.info("The product with id '" + productId + "' was deleted");
        return productDb;
    }

    @Override
    public Product update(Product product) {
        if (!productRepo.existsById(product.getId())) {
            log.warn("The product with name '" + product.getName() + "' was not updated - there is no one product with id " + product.getId());
            throw new EntityExistsException("Продукту з іменем '" + product.getName() + "' не існує");
        }

        Product productDb = productRepo.getById(product.getId());
        product.setCreated(productDb.getCreated());
        product.setUpdated(LocalDate.now());

        productDb = productRepo.save(product);
        log.info("The product with name '" + product.getName() + "' was updated");
        return productDb;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public Product getById(Long productId) {
        if (!productRepo.existsById(productId)) {
            throw new EntityExistsException("Продукт з id '" + productId + "' не існує");
        }
        return productRepo.findById(productId).orElse(null);
    }
}
