package com.Voterid.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "email_otps")
public class EmailOTP  {  

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates auto-incremented ID
	    private Long id;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 6)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private boolean isUsed = false; 

    @PrePersist
    protected void onCreate() {
        expiryTime = LocalDateTime.now().plusMinutes(5); 
    }
}
