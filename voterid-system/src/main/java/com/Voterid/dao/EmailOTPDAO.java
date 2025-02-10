package com.Voterid.dao;

import com.Voterid.pojo.EmailOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmailOTPDAO extends JpaRepository<EmailOTP, Long> {
    Optional<EmailOTP> findByEmailAndOtpAndIsUsedFalse(String email, String otp);
    Optional<EmailOTP> findTopByEmailOrderByIdDesc(String email);
	EmailOTP findByEmail(String email);
    
}