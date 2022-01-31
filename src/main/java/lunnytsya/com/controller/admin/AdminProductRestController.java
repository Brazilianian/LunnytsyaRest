package lunnytsya.com.controller.admin;

import lunnytsya.com.controller.util.ControllerUtils;
import lunnytsya.com.domain.Product;
import lunnytsya.com.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminProductRestController {

    private final ProductService productService;

    public AdminProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            return new ResponseEntity<>(errorMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            product = productService.save(product);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            Long productId = Long.parseLong(id);
            Product product = productService.delete(productId);
            return new ResponseEntity<>(product ,HttpStatus.OK);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    @CrossOrigin(origins = "*")
    @PutMapping
    public ResponseEntity<?> redactProduct(@RequestBody @Valid Product product,
                                           BindingResult bindingResult) {
        if (product == null) {
            return ResponseEntity.badRequest().body(null);
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            return ResponseEntity.unprocessableEntity().body(errors);
        }
        try {
            product = productService.update(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(product);
        }
    }
}
