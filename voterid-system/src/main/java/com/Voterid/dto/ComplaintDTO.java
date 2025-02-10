package com.Voterid.dto;

import com.Voterid.pojo.Complaint;

import lombok.Data;

@Data
public class ComplaintDTO {
    private Long complaintId;
    private String complaintType;
    private String description;
    private String status;
    private String createdDate;

    public ComplaintDTO(Complaint complaint) {
        this.complaintId = complaint.getComplaintId();  
        this.complaintType = complaint.getComplaintType();
        this.description = complaint.getDescription();
        this.status = complaint.getStatus().toString();
        this.createdDate = complaint.getCreatedAt() != null ? complaint.getCreatedAt().toString() : null;
    }
}


