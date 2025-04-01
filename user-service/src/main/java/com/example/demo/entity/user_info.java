package com.example.demo.entity;

import java.util.Set;

import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.id.factory.internal.AutoGenerationTypeStrategy;

import jakarta.persistence.Id;

public class user_info {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles; 

    public user_info() {}

    public user_info(Long id, String name, String email, String password, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
