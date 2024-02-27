package com.otp2.service.impl;

import com.otp2.entity.User;
import com.otp2.exception.ResourceNotFoundException;
import com.otp2.payload.UserDto;
import com.otp2.repository.UserRepository;
import com.otp2.service.EmailService;
import com.otp2.service.EmailVerificationService;
import com.otp2.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Autowired
    private userService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    static  final Map<String,String> emailOtpMapping=new HashMap<>();

    public Map<String,String> verifyOtp(String email,String otp){
        String storeOtp=emailOtpMapping.get(email);
        Map<String,String>response=new HashMap<>();
        if(storeOtp !=null && storeOtp.equals(otp)){
            UserDto dto = userService.getUserByEmail(email);
            if(dto!=null){
                emailOtpMapping.remove(email);
                userService.verifyEmail(dto);
                response.put("status","success");
                response.put("message","Email is verified successFully");
            }
            else{
                response.put("status","error");
                response.put("message","User not Found");
            }
        }else{
            response.put("status","error");
            response.put("message","Invalid Opt");
        }
        return response;

    }

    public Map<String,String> sendOtpForLogin(String email){
        User user = userRepository.findByEmail(email);
        if(user!=null) {
            if (userService.isEmailverified(email)) {
                String otp = emailService.generateOtp();
                emailOtpMapping.put(email, otp);

                emailService.sendOtpEmail(email);

                Map<String, String> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "Otp sent successFully");
                return response;
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("status", "Error");
                response.put("message", "Email is not verified successFully");
                return response;

            }

        }else{
            throw  new ResourceNotFoundException("User not found");

        }

    }

    public Map<String, String> verifyOtpForLogin(String email, String otp) {
        String storeOtp=emailOtpMapping.get(email);
        Map<String,String> response=new HashMap<>();
        if(storeOtp !=null && storeOtp.equals(otp)){
            emailOtpMapping.remove(email);
            response.put("status","Success");
            response.put("message","Otp verified  successFully");
        }
        else{
            response.put("status","Error");
            response.put("message"," Invalid Otp ");
        }
        return  response;

    }
}
