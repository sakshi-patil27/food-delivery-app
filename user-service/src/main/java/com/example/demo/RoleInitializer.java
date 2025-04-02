package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.entity.Role;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> roles = Arrays.asList("ADMIN", "DELIVERY", "CUSTOMER", "RESTAURANT_OWNER");

            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    roleRepository.save(new Role(roleName));
                    System.out.println("Inserted Role: " + roleName);
                }
            }
        };
    }
}
