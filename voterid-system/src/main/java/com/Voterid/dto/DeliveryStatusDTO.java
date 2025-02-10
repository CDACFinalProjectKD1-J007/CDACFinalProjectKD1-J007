package com.Voterid.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.Voterid.pojo.DeliveryStatus;

@Data  
public class DeliveryStatusDTO {

    private Long deliveryId;
    private Long applicationId;
    private String status;  
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
    public DeliveryStatusDTO(DeliveryStatus deliveryStatus) {
        this.deliveryId = deliveryStatus.getDeliveryId();

        
        this.applicationId = (deliveryStatus.getVoterCardApplication() != null) 
                            ? deliveryStatus.getVoterCardApplication().getApplicationId() 
                            : null;

        
        this.status = (deliveryStatus.getStatus() != null) 
                      ? deliveryStatus.getStatus().name() 
                      : "UNKNOWN";

        this.createdAt = deliveryStatus.getCreatedAt();
        this.updatedAt = deliveryStatus.getUpdatedAt();
    }
}
