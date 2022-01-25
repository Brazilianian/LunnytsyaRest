package lunnytsya.com.controller;

import lunnytsya.com.domain.Product;
import lunnytsya.com.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Page<Product>> getAll(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> page = productService.findAll(pageable);

        return ResponseEntity.ok()
                .body(page);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        try {
            Long productId = Long.parseLong(id);
            Product product = productService.getById(productId);
            if (product == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
