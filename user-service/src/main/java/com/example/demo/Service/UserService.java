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

    public user_info registerUser(user_info user, Set<String> roleNames) {
        Optional<user_info> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists with email: " + user.getEmail());
        }

        Set<Role> userRoles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            userRoles.add(role);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

	public user_info getUserByEmail(String email) {
		// TODO Auto-generated method stub
		 Optional<user_info> user = userRepository.findByEmail(email);
		return user.get();
	}
	
	
	public user_info updateUser(user_info user) {
	    user_info existingUser = userRepository.findById(user.getId())
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getId()));

	    existingUser.setName(user.getName());
	    existingUser.setEmail(user.getEmail());

	    // update password if not null/blank
	    if (user.getPassword() != null && !user.getPassword().isBlank()) {
	        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
	    }

	    // directly set roles from user
	    existingUser.setRoles(user.getRoles());

	    return userRepository.save(existingUser);
	}

    
}
