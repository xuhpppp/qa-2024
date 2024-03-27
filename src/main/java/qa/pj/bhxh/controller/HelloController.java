package qa.pj.bhxh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/health-check")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
