package com.Voterid.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.Voterid.pojo.Complaint;

@Data
public class Complaint_ManagementDTO {
    private Long id;
    private String complaintType;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    public Complaint_ManagementDTO(Complaint complaint) {
        this.id = complaint.getComplaintId();
        this.complaintType = complaint.getComplaintType();
        this.description = complaint.getDescription();
        this.status = complaint.getStatus().name();
        this.createdAt = complaint.getCreatedAt();
        this.updatedAt = complaint.getUpdatedAt();
        this.userId = complaint.getUser() != null ? complaint.getUser().getUserId() : null; // Prevent Lazy Loading Issue
    }
}
