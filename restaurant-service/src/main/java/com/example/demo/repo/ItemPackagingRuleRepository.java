package com.example.demo.repo;


import com.example.demo.entity.ItemPackagingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPackagingRuleRepository extends JpaRepository<ItemPackagingRule, Long> {
    // Custom query methods can be added here if necessary
}
