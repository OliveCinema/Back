package com.example.olive_Cinema.service;

import com.example.olive_Cinema.entity.User;
import com.example.olive_Cinema.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(@Valid User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user.get();
    }
}
