package com.Voterid.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Voterid.enums.Gender;
import com.Voterid.enums.Role;
import com.Voterid.enums.Status;


@Getter
@Setter
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    private String name;
    private String email;
    private String password;
    private String district;
    @Enumerated(EnumType.STRING)
    private Role role;    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    private String state;
    private LocalDate dob;
    @Column(unique = true)
    private String voterNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

   
}
