package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.CuisineType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRegistrationRequest {

    private String ownerName;
    private String restaurantName;
    private String email;
    private String mobileNumber;
    private String whatsappNumber;
    private String location;
    private String panCardNumber;
    private String bankAccountNumber;
    private String ifscCode;
    private String fssaiNumber;
    private String fssaiCertificateUrl;
    private CuisineType cuisineType;
    private String menuImageUrl;
    
    private boolean isFixedPackagingCharge;
    private double fixedPackagingCharge;
    private double range1Price;
    private double range2Price;
    private double range3Price;
    private boolean isSameWorkingHoursForAllDays;
    private String commonOpeningTime;
    private String commonClosingTime;
    private List<WorkingHoursDTO> workingHours;
}
