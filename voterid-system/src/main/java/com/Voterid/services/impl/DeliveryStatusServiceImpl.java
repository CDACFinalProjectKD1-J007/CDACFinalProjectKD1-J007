package com.Voterid.services.impl;

import com.Voterid.pojo.DeliveryStatus;
import com.Voterid.dao.DeliveryStatusDAO;
import com.Voterid.dao.VoterCardApplicationDAO;
import com.Voterid.dto.DeliveryStatusDTO;
import com.Voterid.pojo.VoterCardApplication;
import com.Voterid.enums.DeliveryStatusType;
import com.Voterid.services.DeliveryStatusService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusDAO deliveryStatusDAO;
    private final VoterCardApplicationDAO voterCardApplicationDAO;

    public DeliveryStatusServiceImpl(DeliveryStatusDAO deliveryStatusDAO, 
                                     VoterCardApplicationDAO voterCardApplicationDAO) {
        this.deliveryStatusDAO = deliveryStatusDAO;
        this.voterCardApplicationDAO = voterCardApplicationDAO;
    }

  
    @Override
    public DeliveryStatusDTO updateDeliveryStatus(Long id, String newStatus) {
        DeliveryStatusType statusEnum;
        try {
            statusEnum = DeliveryStatusType.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid delivery status: " + newStatus);
        }

        DeliveryStatus deliveryStatus = deliveryStatusDAO.findById(id)
               .orElseThrow(() -> new RuntimeException("Delivery status not found with ID: " + id));

        deliveryStatus.setStatus(statusEnum);
        deliveryStatus.setUpdatedAt(LocalDateTime.now());

        return convertToDTO(deliveryStatusDAO.save(deliveryStatus));
    }

    @Override
    public DeliveryStatusDTO initiateDelivery(Long applicationId) {
        VoterCardApplication voterCardApplication = voterCardApplicationDAO.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Voter card application not found with ID: " + applicationId));

        DeliveryStatus deliveryStatus = new DeliveryStatus();
        deliveryStatus.setVoterCardApplication(voterCardApplication);
        deliveryStatus.setStatus(DeliveryStatusType.IN_PROGRESS);
        deliveryStatus.setCreatedAt(LocalDateTime.now());
        deliveryStatus.setUpdatedAt(LocalDateTime.now());

        return convertToDTO(deliveryStatusDAO.save(deliveryStatus));
    }

    @Override
    public DeliveryStatusDTO getDeliveryById(Long id) {
        return deliveryStatusDAO.findById(id)
               .map(this::convertToDTO)
               .orElseThrow(() -> new RuntimeException("Delivery status not found with ID: " + id));
    }

    @Override
    public List<DeliveryStatusDTO> getAllDeliveries() {
        List<DeliveryStatus> deliveryList = deliveryStatusDAO.findAll();

        if (deliveryList.isEmpty()) {
            throw new RuntimeException("No delivery records found.");
        }

        return deliveryList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DeliveryStatusDTO> getDeliveriesByStatus(String status) {
        DeliveryStatusType statusType;
        try {
            statusType = DeliveryStatusType.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        List<DeliveryStatus> deliveries = deliveryStatusDAO.findByStatus(statusType);
        return deliveries.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryStatusDTO> getDeliveriesByDateRange(LocalDate startDate, LocalDate endDate) {
        return deliveryStatusDAO.findByCreatedAtBetween(
                    startDate.atStartOfDay(), 
                    endDate.atTime(23, 59, 59)
               )
               .stream()
               .map(this::convertToDTO)
               .collect(Collectors.toList());
    } 
    private DeliveryStatusDTO convertToDTO(DeliveryStatus deliveryStatus) {
        return new DeliveryStatusDTO(deliveryStatus);
    }
    

	@Override
	public DeliveryStatusDTO getDeliveryStatusByApplication(Long applicationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeliveryStatusDTO getDeliveryStatus(Long deliveryId) {
		// TODO Auto-generated method stub
		return null;
	}
}
