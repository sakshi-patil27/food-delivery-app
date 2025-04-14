//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.entity.OTPRequest;
//import com.example.demo.entity.OTPVerificationRequest;
//import com.example.demo.service.OTPService;
//
//@RestController
//@RequestMapping("/otp")
//public class OTPController {
//
//    @Autowired
//    private OTPService otpService;
//
//    // Endpoint to generate OTP
//    @PostMapping("/generate")
//    public ResponseEntity<String> generateOTP(@RequestBody OTPRequest otpRequest) {
//        String otp = otpService.generateOTP(otpRequest.getMobileNumber());
//        // Here you would normally send the OTP to the user's phone via SMS
//        return ResponseEntity.ok("OTP sent to " + otpRequest.getMobileNumber() + ": " + otp);
//    }
//
//    // Endpoint to verify OTP
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyOTP(@RequestBody OTPVerificationRequest verificationRequest) {
//        boolean isValid = otpService.verifyOTP(verificationRequest.getMobileNumber(), verificationRequest.getOtp());
//        if (isValid) {
//            return ResponseEntity.ok("OTP verified successfully.");
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
//    }
//}
