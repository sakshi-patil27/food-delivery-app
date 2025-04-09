package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.entity.user_info;
import com.example.demo.entity.Role;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Ensure password encoding
    }
   

	public user_info getUserByEmail(String email) {
		// TODO Auto-generated method stub
		 Optional<user_info> user = userRepository.findByEmail(email);
		return user.get();
	}
	
	public String registerOrUpdateUser(user_info user) {
	    Set<Role> validRoles = new HashSet<>();

	    if (user.getRoles() != null) {
	        for (Role r : user.getRoles()) {
	            Role role = roleRepository.findByName(r.getName())
	                    .orElseThrow(() -> new RuntimeException("Role not found: " + r.getName()));
	            validRoles.add(role);
	        }
	    }

	    if (user.getId() != null) {
	        // 👉 Update flow
	        Optional<user_info> optionalUser = userRepository.findById(user.getId());
	        if (optionalUser.isPresent()) {
	            user_info existingUser = optionalUser.get();
	            existingUser.setName(user.getName());
	            existingUser.setEmail(user.getEmail());

	            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
	            }

	            existingUser.setRoles(validRoles);
	            userRepository.save(existingUser);
	            return "updated";
	        } else {
	            throw new RuntimeException("User not found with ID: " + user.getId());
	        }
	    }

	    // 👉 Register flow
	    Optional<user_info> existing = userRepository.findByEmail(user.getEmail());
	    if (existing.isPresent()) {
	        throw new RuntimeException("User already exists with email: " + user.getEmail());
	    }

	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setRoles(validRoles);
	    userRepository.save(user);

	    return "registered";
	}


    
}
