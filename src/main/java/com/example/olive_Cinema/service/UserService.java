package com.example.olive_Cinema.service;

import com.example.olive_Cinema.entity.app_user;
import com.example.olive_Cinema.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public app_user registerUser(@Valid app_user appuser) {
        if (userRepository.findByUsername(appuser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(appuser);
    }

    public app_user authenticate(String username, String password) {
        Optional<app_user> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user.get();
    }
}
