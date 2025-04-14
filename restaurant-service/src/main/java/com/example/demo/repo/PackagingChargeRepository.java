package com.example.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ItemPackagingRule;

@Repository
public interface PackagingChargeRepository extends JpaRepository<ItemPackagingRule, Long> {
    // Custom query methods can be added here if necessary
}
