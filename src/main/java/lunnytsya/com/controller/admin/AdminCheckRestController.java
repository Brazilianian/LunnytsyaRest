package lunnytsya.com.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/check")
public class AdminCheckRestController {

    @GetMapping("/is-admin")
    public ResponseEntity<?> isAdmin() {
        return ResponseEntity.ok().body(null);
    }
}
