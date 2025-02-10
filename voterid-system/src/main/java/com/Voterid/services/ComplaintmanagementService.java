package com.Voterid.services;

import com.Voterid.pojo.Complaint;
import com.Voterid.dto.Complaint_ManagementDTO;
import java.util.List;

public interface ComplaintmanagementService {

    List<Complaint> getAllComplaints();

    List<Complaint> getComplaintsByUserId(Long userId);

    Complaint getComplaintById(Long id);

    Complaint createComplaint(Complaint complaint);

    List<Complaint_ManagementDTO> getAllComplaintDTOs();

    Complaint updateComplaint(Long id, Complaint complaintDetails);

    boolean deleteComplaint(Long id);
}
