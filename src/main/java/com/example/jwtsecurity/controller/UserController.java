package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.dto.UserResponse;
import com.example.jwtsecurity.service.UserService;

import java.security.Principal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService s;

    public UserController(UserService s) {
        this.s = s;
    }

    @GetMapping("/me")
    public UserResponse me(Principal p) {
        return s.me(p.getName());
    }
}
