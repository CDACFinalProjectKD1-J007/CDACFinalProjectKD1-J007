package com.Voterid.services.impl;

import com.Voterid.dao.DeliveryStatusDAO;
import com.Voterid.dao.UserDAO;
import com.Voterid.dao.VoterCardApplicationDAO;
import com.Voterid.dto.VoterCardApplicationDTO;
import com.Voterid.pojo.DeliveryStatus;
import com.Voterid.pojo.User;
import com.Voterid.pojo.VoterCardApplication;
import com.Voterid.enums.ApplicationStatus;
import com.Voterid.services.VoterCardApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoterCardApplicationServiceImpl implements VoterCardApplicationService {

    @Autowired
    private VoterCardApplicationDAO applicationDAO;
    @Autowired
    private DeliveryStatusDAO deliveryStatusDAO;
    @Autowired
    private UserDAO userDAO;
    

    @Override
    public List<VoterCardApplication> getAllApplications() {
        return applicationDAO.findAll();
    }

    @Override
    public List<VoterCardApplication> getApplicationsByUserId(Long userId) {
        return applicationDAO.findByUser_UserId(userId);
    }

    @Override
    public VoterCardApplication getApplicationById(Long id) {
        return applicationDAO.findById(id)
            .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    @Override
    public VoterCardApplication createApplication(VoterCardApplication application) {
        application.setStatus(ApplicationStatus.Pending);
        return applicationDAO.save(application);
    }
    public VoterCardApplication applyForVoterCard(Long userId, VoterCardApplication application) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));  

        application.setUser(user);  
        return applicationDAO.save(application);
    }

    @Override
    public VoterCardApplication updateApplication(Long id, VoterCardApplication applicationDetails) {
        VoterCardApplication application = getApplicationById(id);
        application.setStatus(applicationDetails.getStatus());
        return applicationDAO.save(application);
    }

    @Override
    public List<VoterCardApplicationDTO> getApplicationsByStatus(String status) {
        try {
            ApplicationStatus enumStatus = ApplicationStatus.valueOf(status);
            List<VoterCardApplication> applications = applicationDAO.findByStatus(enumStatus);
            return applications.stream()
                    .map(VoterCardApplicationDTO::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    @Override
    public VoterCardApplicationDTO updateApplicationStatus(Long id, String status) {
        VoterCardApplication application = getApplicationById(id);
        try {
            application.setStatus(ApplicationStatus.valueOf(status));
            application = applicationDAO.save(application);
            return new VoterCardApplicationDTO(application);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }
   



    @Override
    public DeliveryStatus getDeliveryStatusByApplication(Long applicationId) {
        return deliveryStatusDAO.findByVoterCardApplication_ApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("No delivery status found for application ID: " + applicationId));
    }

}
