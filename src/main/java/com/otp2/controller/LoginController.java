package com.otp2.controller;

import com.otp2.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/send-otp-for-login")
    public ResponseEntity<Map<String,String>> sendOtpForLogin(@RequestParam String email){
        Map<String, String> stringStringMap = emailVerificationService.sendOtpForLogin(email);
        return new ResponseEntity<>(stringStringMap, HttpStatus.NOT_FOUND);

    }
    @PostMapping("/verify-otp-for-login")
    public Map<String,String> verifyOtpForLogin(@RequestParam String email,@RequestParam String otp){
        return emailVerificationService.verifyOtpForLogin(email,otp);
    }
}
