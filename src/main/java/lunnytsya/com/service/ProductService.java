package lunnytsya.com.service;

import lunnytsya.com.domain.Product;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.ProductRepo;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService implements IService<Product> {

    final static Logger logger = Logger.getLogger(String.valueOf(ProductService.class));

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public void save(Product product) {
        productRepo.save(product);
        logger.info("The product with name '" + product.getName() + "' was saved");
    }

    @Override
    public void delete(Long productId) {
        if (productRepo.existsById(productId)) {
            productRepo.deleteById(productId);
            logger.info("The product with id '" + productId + "' was deleted");
        } else {
            logger.warn("The product with id '" + productId + "' was not delete - there is no one product with id " + productId);
        }
    }

    @Override
    public void update(Product product) {
        if (productRepo.existsById(product.getId())) {
            productRepo.save(product);
            logger.info("The product with name '" + product.getName() + "' was updated");
        } else {
            logger.warn("The product with name '" + product.getName() + "' was not updated - there is no one product with id " + product.getId());
        }
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public Product getById(Long productId) {
        try {
            return productRepo.findById(productId).orElse(null);
        } catch (EntityNotFoundException e) {
            return null;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}
