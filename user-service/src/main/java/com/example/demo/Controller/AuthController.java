package com.example.demo.Controller;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.user_info;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/auth/login")
    public String login(@RequestBody AuthRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // If authentication is successful, generate a JWT or return success message
        return "Login successful!";
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody user_info user) {
        try {
            // Encode password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (user.getId() != null && user.getId() > 0) {
                userRepository.update(user);
                return "User updated successfully!";
            } else { 
                userRepository.save(user);
                return "User registered successfully!";
       }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

