package com.otp2.service;

import java.util.Map;

public interface EmailVerificationService {
    public Map<String,String> verifyOtp(String email, String otp);

    Map<String, String> sendOtpForLogin(String email);

    Map<String, String> verifyOtpForLogin(String email, String otp);
}
