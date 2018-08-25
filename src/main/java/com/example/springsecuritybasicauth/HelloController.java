package com.example.springsecuritybasicauth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mtumilowicz on 2018-08-24.
 */
@RestController
public class HelloController {
    
    @GetMapping("admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("hello admin");
    }

    @GetMapping("user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("hello user");
    }
    
    @GetMapping("health")
    public void health() {}
}
