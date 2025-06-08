package com.bitbyashish.neighborhood_service_directory.service;

import com.bitbyashish.neighborhood_service_directory.dto.SignupRequest;
import com.bitbyashish.neighborhood_service_directory.entity.User;
import com.bitbyashish.neighborhood_service_directory.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        // Set active true by default if not set
        if (user.isActive() == false) {
            user.setActive(true);
        }
        return repository.save(user);
    }

    public User getUserById(Long id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllActiveUsers() {
        return repository.findAll().stream()
                .filter(User::isActive)
                .toList();
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        // update other fields as needed
        return repository.save(user);
    }

    @Transactional
    public void softDeleteUser(Long id) {
        User user = repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        repository.save(user);
    }

    public void registerUser(SignupRequest request) {
    if (repository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already exists");
    }

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    if (request.getRoles() == null || request.getRoles().isEmpty()) {
        user.setRoles(Set.of("ROLE_USER"));
    } else {
        user.setRoles(request.getRoles().stream().map(role ->
                role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase()
        ).collect(Collectors.toSet()));
    }

    repository.save(user);
}
}
