package com.Voterid.controller;

import com.Voterid.dto.ResetPasswordRequest;
import com.Voterid.services.OTPService;
import com.Voterid.services.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private UserServiceImpl userService;  

    @PostMapping("/send")
    public ResponseEntity<?> sendOTP(@RequestParam String email) {
        try {
            otpService.sendOTP(email);
            return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send OTP"));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.verifyOTP(email, otp);
        if (isValid) {
            return ResponseEntity.ok(Map.of("message", "OTP verified successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired OTP"));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            boolean success = userService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
            if (success) {
                return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
            }
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired OTP"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // e.g., "User not found"
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }
}
