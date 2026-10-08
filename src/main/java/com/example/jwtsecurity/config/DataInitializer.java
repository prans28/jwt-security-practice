package com.example.jwtsecurity.config;

import com.example.jwtsecurity.entity.*;
import com.example.jwtsecurity.entity.Role;
import com.example.jwtsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository r, PasswordEncoder p) {
        return args -> {
            create(r, p, "Normal User", "user@example.com", "User@123", Role.USER);
            create(r, p, "Admin User", "admin@example.com", "Admin@123", Role.ADMIN);
            create(r, p, "Super Admin", "superadmin@example.com", "SuperAdmin@123", Role.SUPER_ADMIN);
        };
    }

    private void create(UserRepository r, PasswordEncoder p, String n, String e, String pw, Role role) {
        if (!r.existsByEmail(e)) {
            User u = new User();
            u.setName(n);
            u.setEmail(e);
            u.setPassword(p.encode(pw));
            u.setRole(role);
            r.save(u);
        }
    }
}
