package com.Voterid.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.Voterid.pojo.VoterCardApplication;
import com.Voterid.enums.ApplicationStatus;

@Data
@NoArgsConstructor 
public class VoterCardApplicationDTO {
    private Long applicationId;
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

   
    public VoterCardApplicationDTO(VoterCardApplication application) {
        this.applicationId = application.getApplicationId();
        this.userId = application.getUser().getUserId();
        this.status = application.getStatus().name();
        this.createdAt = application.getCreatedAt();
        this.updatedAt = application.getUpdatedAt();
    }

   
    public VoterCardApplication toEntity() {
        VoterCardApplication application = new VoterCardApplication();
        application.setApplicationId(this.applicationId);
       
        application.setStatus(ApplicationStatus.valueOf(this.status));
        application.setCreatedAt(this.createdAt);
        application.setUpdatedAt(this.updatedAt);
        return application;
    }
}
