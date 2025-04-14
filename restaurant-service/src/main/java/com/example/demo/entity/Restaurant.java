package com.example.demo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;

    private String restaurantName;

    private String email;

    private String mobileNumber;

    private String whatsappNumber;

    private String location;

    private boolean sameWorkingHoursForAllDays;

    @ElementCollection
    @CollectionTable(name = "restaurant_working_hours", joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyColumn(name = "day")
    private Map<DayOfWeek, WorkingHours> workingHours = new HashMap<>();

    private String panCardNumber;

    private String bankAccountNumber;

    private String ifscCode;

    private String fssaiNumber;

    private String fssaiCertificateUrl; // Store S3 or local path

    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;

    private String menuImageUrl;

    @Enumerated(EnumType.STRING)
    private PackagingChargeType packagingChargeType;
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemPackagingRule> packagingRules;

    private double fixedPackagingCharge;

//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
//    private List<ItemPackagingRule> packagingRules;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Offer> offers;
}
