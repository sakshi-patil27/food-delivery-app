package com.example.demo.Repository;

import com.example.demo.entity.user_info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user_info, Long> {
    Optional<user_info> findByEmail(String email);
}
