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
    
    
}
