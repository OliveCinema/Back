package com.example.olive_Cinema.controller;

import com.example.olive_Cinema.entity.User;
import com.example.olive_Cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.authenticate(username, password);
        return ResponseEntity.ok("Login successful. Welcome, " + user.getUsername() + "!");
    }
}
