package com.example.demo.entity;

public class OTPVerificationRequest {
    private String mobileNumber;
    private String otp;
    // Getters and setters
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
}
