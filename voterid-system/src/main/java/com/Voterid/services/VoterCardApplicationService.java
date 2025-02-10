package com.Voterid.services;

import com.Voterid.dto.VoterCardApplicationDTO;
import com.Voterid.pojo.DeliveryStatus;
import com.Voterid.pojo.VoterCardApplication;
import java.util.List;

public interface VoterCardApplicationService {
    List<VoterCardApplicationDTO> getApplicationsByStatus(String status);

    VoterCardApplication getApplicationById(Long id);

    List<VoterCardApplication> getAllApplications();

    VoterCardApplication createApplication(VoterCardApplication application);

    VoterCardApplication updateApplication(Long id, VoterCardApplication application);  // ✅ Ensure this is present

    VoterCardApplicationDTO updateApplicationStatus(Long id, String status);  // ✅ Ensure this is present
    
	List<VoterCardApplication> getApplicationsByUserId(Long userId);
	DeliveryStatus getDeliveryStatusByApplication(Long applicationId);
}
