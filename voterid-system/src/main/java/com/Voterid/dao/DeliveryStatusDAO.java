package com.Voterid.dao;

import com.Voterid.pojo.DeliveryStatus;
import com.Voterid.enums.DeliveryStatusType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryStatusDAO extends JpaRepository<DeliveryStatus, Long> {
    
    List<DeliveryStatus> findByStatus(DeliveryStatusType status);
    
    List<DeliveryStatus> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<DeliveryStatus> findByVoterCardApplication_ApplicationId(Long applicationId);
    boolean existsByVoterCardApplication_ApplicationId(Long applicationId);


}
