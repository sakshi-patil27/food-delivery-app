package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.RestaurantRegistrationRequest;
import com.example.demo.dto.WorkingHoursDTO;
import com.example.demo.entity.DayOfWeek;
import com.example.demo.entity.ItemPackagingRule;
import com.example.demo.entity.Restaurant;
import com.example.demo.entity.WorkingHours;
import com.example.demo.repo.ItemPackagingRuleRepository;
import com.example.demo.repo.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ItemPackagingRuleRepository itemPackagingRuleRepository;

    @Transactional
    public Restaurant registerRestaurant(RestaurantRegistrationRequest request) {
        // Step 1: Create Restaurant entity
        Restaurant restaurant = new Restaurant();
        restaurant.setOwnerName(request.getOwnerName());
        restaurant.setRestaurantName(request.getRestaurantName());
        restaurant.setEmail(request.getEmail());
        restaurant.setMobileNumber(request.getMobileNumber());
        restaurant.setWhatsappNumber(request.getWhatsappNumber());
        restaurant.setLocation(request.getLocation());
        restaurant.setPanCardNumber(request.getPanCardNumber());
        restaurant.setBankAccountNumber(request.getBankAccountNumber());
        restaurant.setIfscCode(request.getIfscCode());
        restaurant.setFssaiNumber(request.getFssaiNumber());
        restaurant.setFssaiCertificateUrl(request.getFssaiCertificateUrl());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setMenuImageUrl(request.getMenuImageUrl());

        // Step 2: Handle Working Hours
        if (request.isSameWorkingHoursForAllDays()) {
            // Set same working hours for all days
            WorkingHours commonWorkingHours = new WorkingHours(request.getCommonOpeningTime(), request.getCommonClosingTime());
            Map<DayOfWeek, WorkingHours> workingHoursMap = new HashMap<>();
            for (DayOfWeek day : DayOfWeek.values()) {
                workingHoursMap.put(day, commonWorkingHours);
            }
            restaurant.setWorkingHours(workingHoursMap);
        } else {
            // Set different working hours for each day
            Map<DayOfWeek, WorkingHours> workingHoursMap = new HashMap<>();
            for (WorkingHoursDTO hoursDTO : request.getWorkingHours()) {
                DayOfWeek day = DayOfWeek.valueOf(hoursDTO.getDay().toUpperCase());
                WorkingHours workingHours = new WorkingHours(hoursDTO.getOpeningTime(), hoursDTO.getClosingTime());
                workingHoursMap.put(day, workingHours);
            }
            restaurant.setWorkingHours(workingHoursMap);
        }

        // Step 3: Handle Packaging Charge Rules
        List<ItemPackagingRule> packagingRules = new ArrayList<>();
        if (request.isFixedPackagingCharge()) {
            packagingRules.add(new ItemPackagingRule(null, 0.0, Double.MAX_VALUE, request.getFixedPackagingCharge(), restaurant));
        } else {
            packagingRules.add(new ItemPackagingRule(null, 0.0, 5.0, request.getRange1Price(), restaurant));
            packagingRules.add(new ItemPackagingRule(null, 6.0, 10.0, request.getRange2Price(), restaurant));
            packagingRules.add(new ItemPackagingRule(null, 11.0, Double.MAX_VALUE, request.getRange3Price(), restaurant));
        }
        restaurant.setPackagingRules(packagingRules);

        // Step 4: Save the Restaurant entity
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant;
    }
}
