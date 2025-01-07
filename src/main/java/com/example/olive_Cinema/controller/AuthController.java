package com.example.olive_Cinema.controller;

import com.example.olive_Cinema.entity.app_user;
import com.example.olive_Cinema.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor

public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<app_user> register(@Valid @RequestBody app_user appuser) {
        app_user registeredAppuser = userService.registerUser(appuser);
        return ResponseEntity.ok(registeredAppuser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        app_user appuser = userService.authenticate(username, password);
        return ResponseEntity.ok("Login successful. Welcome, " + appuser.getUsername() + "!");
    }
}
