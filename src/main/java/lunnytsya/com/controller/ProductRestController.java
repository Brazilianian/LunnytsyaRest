package lunnytsya.com.controller;

import com.sun.xml.bind.v2.TODO;
import lunnytsya.com.controller.util.ControllerUtils;
import lunnytsya.com.domain.Product;
import lunnytsya.com.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            product = productService.save(product);
        } catch (EntityExistsException e) {
            // FIXME: 25.04.2022 ITS REALLY SAD
            HashMap<Object, Object> validation = new HashMap<>();
            validation.put("name", String.format("Продукт з іменем '%s' вже існує", product.getName()));
            return new ResponseEntity<>(validation, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Page<Product>> getAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> page = productService.findAll(pageable);

        return ResponseEntity.ok().body(page);
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

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            Long productId = Long.parseLong(id);
            Product product = productService.delete(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping
    public ResponseEntity<?> redactProduct(@RequestBody @Valid Product product,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        if (product == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            product = productService.update(product);
            return ResponseEntity.ok(product);
            // FIXME: 25.04.2022 ITS REALLY SAD
        } catch (EntityExistsException e) {
            HashMap<Object, Object> validation = new HashMap<>();
            validation.put("name", String.format("Продукт з іменем '%s' вже існує", product.getName()));
            return new ResponseEntity<>(validation, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(product);
        }
    }
}
