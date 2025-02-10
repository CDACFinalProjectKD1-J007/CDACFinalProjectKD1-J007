package com.Voterid.services;

import com.Voterid.pojo.Complaint;
import java.util.List;

public interface ComplaintService {

    List<Complaint> getAllComplaints();

    List<Complaint> getComplaintsByUserId(Long userId);

    Complaint getComplaintById(Long id);

    Complaint createComplaint(Complaint complaint);

    Complaint updateComplaint(Long id, Complaint complaintDetails);

    boolean deleteComplaint(Long id);
}
