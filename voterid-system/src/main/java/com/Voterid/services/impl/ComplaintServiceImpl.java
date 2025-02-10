package com.Voterid.services.impl;

import com.Voterid.pojo.Complaint;
import com.Voterid.services.ComplaintService;
import com.Voterid.dao.ComplaintDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintDao ComplaintDAO;
    
    public List<Complaint> getAllComplaints() {
        return ComplaintDAO.findAll();
    }
    
    public List<Complaint> getComplaintsByUserId(Long userId) {
        return ComplaintDAO.findByUser_UserId(userId);
    }
    
    public Complaint getComplaintById(Long id) {
        return ComplaintDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + id));
    }
    
    public Complaint createComplaint(Complaint complaint) {
        return ComplaintDAO.save(complaint);
    }

    public Complaint updateComplaint(Long id, Complaint complaintDetails) {
        Complaint complaint = getComplaintById(id);
        complaint.setStatus(complaintDetails.getStatus());
        complaint.setDescription(complaintDetails.getDescription());
        return ComplaintDAO.save(complaint);
    }

    public boolean deleteComplaint(Long id) {
        if (!ComplaintDAO.existsById(id)) {
            return false;
        }
        ComplaintDAO.deleteById(id);
        return true;
    }

}
