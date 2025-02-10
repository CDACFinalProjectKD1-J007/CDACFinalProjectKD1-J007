package com.Voterid.services;

public interface OTPService {

    void sendOTP(String email);

    boolean verifyOTP(String email, String otp);
}
