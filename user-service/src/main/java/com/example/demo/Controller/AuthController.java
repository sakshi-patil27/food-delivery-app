package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.OtpService;
import com.example.demo.Service.UserService;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.EmailRequest;
import com.example.demo.entity.OtpRequest;
import com.example.demo.entity.user_info;
import com.example.demo.security.AuthService;
import com.example.demo.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {


	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private OtpService otpService;

	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOtp(@RequestBody EmailRequest request) {
	    String otp = otpService.generateOtp(request.getEmail());
	    return ResponseEntity.ok("OTP sent to your email.");
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {
	    boolean isValid = otpService.verifyOtp(request.getEmail(), request.getOtp());
	    if (isValid) {
	        return ResponseEntity.ok("OTP Verified Successfully!");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or Expired OTP.");
	    }
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest request) {
	    try {
	        // Authenticate and generate token
	        String token = authService.login(request.getEmail(), request.getPassword());

	        // Fetch user details
	        user_info user = userService.getUserByEmail(request.getEmail());
	        if (user == null) {
	            throw new RuntimeException("User not found with email: " + request.getEmail());
	        }

	        // Construct user info map
	        Map<String, Object> userInfo = new HashMap<>();
	        userInfo.put("name", user.getName());
	        userInfo.put("email", user.getEmail());

	        // Construct final response map
	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("user", userInfo);

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

	
	@PostMapping("/register")
	public ResponseEntity<String> registerOrUpdateUser(@RequestBody user_info user) {
	    try {
	        String action = userService.registerOrUpdateUser(user);
	        return ResponseEntity.ok("User " + action + " successfully!");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
	 
	 
	   @PreAuthorize("hasRole('RESTAURANT_OWNER')")
	    @GetMapping("/place")
	    public ResponseEntity<String> placeOrder() {
	        return ResponseEntity.ok("Order placed successfully!");
	    }
	   
	   @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/getUsers")
	    public ResponseEntity<?> getUsers() {
		   try {
			   List<user_info> list=userService.getuser();
			   return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body(list);
			 
		   } catch(Exception e) {
			   return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body("Error: " + e.getMessage());
		   }
	        
	   }
	   
	   
	   @PreAuthorize("hasRole('ADMIN')")
	   @PostMapping("/getUserByEmail")
	   public ResponseEntity<?> getUsersByEmail(@RequestBody EmailRequest request) {
	       try {
	           user_info user = userService.getUserByEmail(request.getEmail());
	           return ResponseEntity.ok(user);
	       } catch (Exception e) {
	           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                .body("Error: " + e.getMessage());
	       }
	   }

	}
