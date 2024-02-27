package com.otp2.service;

public interface EmailService {
    public String generateOtp();

    public void sendOtpEmail(String email);
    public void sendEmail(String to,String subject,String text);
}
