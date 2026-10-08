package com.example.jwtsecurity.service;

import com.example.jwtsecurity.dto.*;
import com.example.jwtsecurity.entity.*;
import com.example.jwtsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository r) {
        repo = r;
    }

    private User user(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public UserResponse me(String email) {
        return UserResponse.from(repo.findByEmail(email).orElseThrow());
    }

    public List<UserResponse> all() {
        return repo.findAll().stream().map(UserResponse::from).toList();
    }

    public UserResponse byId(Long id) {
        return UserResponse.from(user(id));
    }

    public UserResponse update(Long id, UpdateUserRequest r) {
        User u = user(id);
        u.setName(r.name());
        u.setEmail(r.email());
        return UserResponse.from(repo.save(u));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NoSuchElementException("User not found");
        repo.deleteById(id);
    }

    public UserResponse role(Long id, RoleRequest r) {
        User u = user(id);
        u.setRole(r.role());
        return UserResponse.from(repo.save(u));
    }
}
