package com.Voterid.services.impl;

import com.Voterid.dao.EmailOTPDAO;
import com.Voterid.pojo.EmailOTP;
import com.Voterid.services.EmailService;
import com.Voterid.services.OTPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OTPServiceImpl implements  OTPService{
    
    @Autowired
    private EmailOTPDAO otpDAO;
    
    @Autowired
    private EmailService emailService;
    public void sendOTP(String email) {
        String otp = generateOTP();
        
        EmailOTP emailOTP = new EmailOTP();
        emailOTP.setEmail(email);
        emailOTP.setOtp(otp);
     
        otpDAO.save(emailOTP);
        
        String subject = "Your OTP Code";
        String body = "Your OTP code is: " + otp + "\nThis code will expire in 5 minutes.";
        emailService.sendEmail(email, subject, body);
    }
    public boolean verifyOTP(String email, String otp) {
       EmailOTP storedOtp = otpDAO.findByEmail(email);

        if (storedOtp == null) {
            System.out.println("No OTP found for email: " + email);
            return false;
        }

        System.out.println("Stored OTP: " + storedOtp.getOtp());
        System.out.println("Received OTP: " + otp);
        
       
        if (storedOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            System.out.println("OTP has expired!");
            return false;
        }

        
        return storedOtp.getOtp().equals(otp);
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}