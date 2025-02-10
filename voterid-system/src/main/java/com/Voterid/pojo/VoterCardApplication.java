package com.Voterid.pojo;

import com.Voterid.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "voter_card_applications",uniqueConstraints = {
	    @UniqueConstraint(columnNames = "user_id")})
public class VoterCardApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true) 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Prevent Lazy Loading Issues
    private User user;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "voterCardApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference  // Ensures DeliveryStatus is serialized properly
    private DeliveryStatus deliveryStatus; 

   

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
