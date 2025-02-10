package com.Voterid.pojo;

import com.Voterid.enums.DeliveryStatusType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "delivery_status")
public class DeliveryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @OneToOne
    @JoinColumn(name = "application_id", referencedColumnName = "applicationId", unique = true)
    @JsonBackReference  // Prevents Infinite Recursion
    private VoterCardApplication voterCardApplication;

    @Enumerated(EnumType.STRING)
    private DeliveryStatusType status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

   

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
