package com.example.demo.Controller;

import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.RegisterRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.user_info;
import com.example.demo.security.AuthService;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

import java.util.Collections;
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
    public String login1(@RequestBody AuthRequest reuqest) {
    	try {
    		return authService.login(reuqest.getEmail(),reuqest.getPassword());
    	          
    	} catch (Exception e) {
			return "Error:: " + e.getMessage();
		}
     
    }
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request) { 
		try {
			user_info user = new user_info();
			user.setName(request.getName());
			user.setEmail(request.getEmail());
			user.setPassword(request.getPassword());

			userService.registerUser(user, request.getRoles());
			return "User registered successfully!";
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	 @GetMapping("/validate")
	    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
	        try {
	            // Extract JWT token from the "Bearer token"
	            if (token.startsWith("Bearer ")) {
	                token = token.substring(7);
	            }

	            boolean isValid = jwtTokenProvider.validateToken(token);
	            if (isValid) {
	                String userEmail = jwtTokenProvider.getUsernameFromToken(token);
	                return ResponseEntity.ok("Token is valid for user: " + userEmail);
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }
	    }
	}
