package com.example.jwtsecurity.service;

import com.example.jwtsecurity.dto.*;
import com.example.jwtsecurity.entity.*;
import com.example.jwtsecurity.repository.UserRepository;
import com.example.jwtsecurity.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtService jwt;

    public AuthService(UserRepository r, PasswordEncoder e, AuthenticationManager m, JwtService j) {
        repo = r;
        encoder = e;
        manager = m;
        jwt = j;
    }

    public UserResponse register(RegisterRequest r) {
        if (repo.existsByEmail(r.email())) throw new IllegalArgumentException("Email already exists");
        User u = new User();
        u.setName(r.name());
        u.setEmail(r.email());
        u.setPassword(encoder.encode(r.password()));
        u.setRole(Role.USER);
        return UserResponse.from(repo.save(u));
    }

    public AuthResponse login(LoginRequest r) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(r.email(), r.password()));
        User u = repo.findByEmail(r.email()).orElseThrow();
        return tokens(u);
    }

    public AuthResponse refresh(RefreshRequest r) {
        String email;
        try {
            email = jwt.username(r.refreshToken());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        User u = repo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!jwt.valid(r.refreshToken(), email, "refresh"))
            throw new IllegalArgumentException("Invalid or expired refresh token");
        return tokens(u);
    }

    private AuthResponse tokens(User u) {
        return new AuthResponse(jwt.access(u), jwt.refresh(u), "Bearer", jwt.getAccessMs() / 1000, jwt.getRefreshMs() / 1000);
    }
}
