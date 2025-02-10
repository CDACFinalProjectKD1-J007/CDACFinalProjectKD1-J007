package com.Voterid.services;

import com.Voterid.dto.DeliveryStatusDTO;
import java.time.LocalDate;
import java.util.List;

public interface DeliveryStatusService {
    
    List<DeliveryStatusDTO> getAllDeliveries();
    
    DeliveryStatusDTO getDeliveryById(Long id);

    List<DeliveryStatusDTO> getDeliveriesByStatus(String status);

    List<DeliveryStatusDTO> getDeliveriesByDateRange(LocalDate startDate, LocalDate endDate);

    DeliveryStatusDTO updateDeliveryStatus(Long id, String newStatus);

    DeliveryStatusDTO initiateDelivery(Long applicationId);

    DeliveryStatusDTO getDeliveryStatusByApplication(Long applicationId);

    DeliveryStatusDTO getDeliveryStatus(Long deliveryId);
}
