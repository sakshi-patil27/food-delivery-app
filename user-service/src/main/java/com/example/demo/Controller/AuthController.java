package com.example.demo.Controller;

import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.RegisterRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.user_info;
import com.example.demo.security.AuthService;
import com.example.demo.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import aj.org.objectweb.asm.TypeReference;

import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
	    try {
	        user_info user = new user_info();
	        user.setName(request.getName());
	        user.setEmail(request.getEmail());
	        user.setPassword(request.getPassword());

	        userService.registerUser(user, request.getRoles());

	        return ResponseEntity.ok("User registered successfully!");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error: " + e.getMessage());
	    }
	}

	 @GetMapping("/validate")
	    public ResponseEntity<?> validateToken() {
	        try {
	        	
	        	return ResponseEntity.ok("Token is valid for user: "); 
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }
	    }
	}
