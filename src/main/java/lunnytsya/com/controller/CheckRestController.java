package lunnytsya.com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/check")
public class CheckRestController {

    @GetMapping("/auth")
    public ResponseEntity<?> checkAuth() {
        try {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            throw e;
        }
    }
}
