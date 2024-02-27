package com.otp2.controller;

import com.otp2.payload.UserDto;
import com.otp2.service.EmailService;
import com.otp2.service.EmailVerificationService;
import com.otp2.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private userService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody UserDto userDto){
        UserDto dto = userService.registerUser(userDto);

        //send OTP email for email verification
        emailService.sendOtpEmail(dto.getEmail());
        Map<String,String>response=new HashMap<>();
        response.put("Status","Success");
        response.put("message","User registered Successfully. Check Your email for Verification.");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/verify-otp")
    public  Map<String,String>verifyOtp(@RequestParam String email, @RequestParam String otp){

        Map<String, String> stringStringMap = emailVerificationService.verifyOtp(email, otp);

        return stringStringMap;
    }

}
