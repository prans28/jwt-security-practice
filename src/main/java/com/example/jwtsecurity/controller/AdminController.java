package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.dto.*;
import com.example.jwtsecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {
    private final UserService s;

    public AdminController(UserService s) {
        this.s = s;
    }

    @GetMapping
    public List<UserResponse> all() {
        return s.all();
    }

    @GetMapping("/{id}")
    public UserResponse one(@PathVariable Long id) {
        return s.byId(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest r) {
        return s.update(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        s.delete(id);
        return ResponseEntity.noContent().build();
    }
}
