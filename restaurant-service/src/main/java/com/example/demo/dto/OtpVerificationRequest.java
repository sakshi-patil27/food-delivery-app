package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpVerificationRequest {
    private String emailOrWhatsapp;
    private String otp;
}
