package com.example.demo.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.MenuSetupRequest;
import com.example.demo.dto.OtpVerificationRequest;
import com.example.demo.dto.RestaurantRegistrationRequest;
import com.example.demo.service.OTPService;
import com.example.demo.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
@Autowired
    private  RestaurantService restaurantService;
@Autowired
private  OTPService otpService;
    // 1. Register Restaurant
    @PostMapping("/register")
    public ResponseEntity<?> registerRestaurant(@RequestBody RestaurantRegistrationRequest request) {
        return ResponseEntity.ok(restaurantService.registerRestaurant(request));
    }

//    // 3. Upload PAN, Bank, and FSSAI certificate
//    @PostMapping("/upload-documents")
//    public ResponseEntity<?> uploadDocuments(
//            @RequestParam Long restaurantId,
//            @RequestParam String panNumber,
//            @RequestParam String bankAccountNo,
//            @RequestParam String ifscCode,
//            @RequestParam MultipartFile fssaiCertificate
//    ) {
//        return ResponseEntity.ok(
//                restaurantService.uploadDocuments(
//                        restaurantId, panNumber, bankAccountNo, ifscCode, fssaiCertificate
//                )
//        );
//    }
//
//    // 4. Menu setup - Food type, cuisine, menu image
//    @PostMapping("/menu-setup")
//    public ResponseEntity<?> setupMenu(@RequestBody MenuSetupRequest request) {
//        return ResponseEntity.ok(restaurantService.setupMenu(request));
//    }
//
//    // 5. Packaging Charges setup
//    @PostMapping("/packaging-setup")
//    public ResponseEntity<?> setupPackagingCharges(@RequestBody PackagingChargeRequest request) {
//        return ResponseEntity.ok(restaurantService.setupPackagingCharges(request));
//    }
}
