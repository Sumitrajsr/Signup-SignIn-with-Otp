package com.otp2.service.impl;

import com.otp2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import static com.otp2.service.impl.EmailVerificationServiceImpl.emailOtpMapping;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public String generateOtp() {
        return String.format("%06d", new java.util.Random().nextInt(1000000));
    }

    @Override
    public void sendOtpEmail(String email) {
        String otp=generateOtp();
        emailOtpMapping.put(email,otp);
        sendEmail(email,"OTP for Email Verification","Your OTP is: "+otp);

    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("sumitrajsr.174@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }


}
