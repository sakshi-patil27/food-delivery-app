package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OtpVerificationRequest;

@Service
public class OTPService {

    private final Map<String, String> otpStore = new HashMap<>(); // Store OTPs temporarily (for demo purposes)
    private final long OTP_VALIDITY_DURATION = 5 * 60 * 1000; // OTP validity for 5 minutes

    // Generate OTP
    public String generateOTP(String mobileNumber) {
        String otp = String.valueOf((int) (Math.random() * 1000000)); // Random 6-digit OTP
        otpStore.put(mobileNumber, otp);

        // Set OTP expiry time (using Thread.sleep for simplicity, but consider using a scheduled task for actual projects)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                otpStore.remove(mobileNumber);
            }
        }, OTP_VALIDITY_DURATION);

        return otp;
    }

    // Verify OTP
    public boolean verifyOTP(OtpVerificationRequest request) {
        return request.getOtp().equals(otpStore.get(request.getEmailOrWhatsapp()));
    }
}
