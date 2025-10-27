package com.suems.controller;


import com.suems.dto.AuthResponse;
import com.suems.dto.LoginRequest;
import com.suems.dto.RegisterRequest;
import com.suems.model.User;
import com.suems.repository.UserRepository;
import com.suems.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwt;

    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwt) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req)
    {
        userRepository.findByEmail(req.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email Already Exists!");
        });
        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(u);

        String token = jwt.generateToken(u.getEmail(), u.getRole().name());
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getEmail(), u.getRole().name()));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req)
    {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(()->new RuntimeException("Invalid Credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Invalid Credentials");
        }
        String token = jwt.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getEmail(), user.getRole().name()));
    }

    @GetMapping("/profile")
    public Map<String, Object> profile(@org.springframework.security.core.annotation.AuthenticationPrincipal com.suems.model.User user) {
        return Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "role", user.getRole().name()
        );
    }

}
