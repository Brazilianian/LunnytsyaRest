package lunnytsya.com.service;

import lunnytsya.com.domain.Product;
import lunnytsya.com.interfaces.IService;
import lunnytsya.com.repository.ProductRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void delete(Product product) {
        if (productRepo.existsById(product.getId())) {
            productRepo.deleteById(product.getId());
            logger.info("The product with name '" + product.getName() + "' was deleted");
        } else {
            logger.warn("The product with name '" + product.getName() + "' was not delete - there is no one product with id " + product.getId());
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

    public List<Product> findAll() {
        return productRepo.findAll();
    }
}
