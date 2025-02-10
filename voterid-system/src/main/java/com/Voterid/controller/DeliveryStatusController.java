package com.Voterid.controller;

import com.Voterid.dao.DeliveryStatusDAO;
import com.Voterid.dao.VoterCardApplicationDAO;
import com.Voterid.dto.DeliveryStatusDTO;
import com.Voterid.pojo.DeliveryStatus;
import com.Voterid.pojo.VoterCardApplication;
import com.Voterid.enums.ApplicationStatus;
import com.Voterid.enums.DeliveryStatusType;
import com.Voterid.services.DeliveryStatusService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery-status")
@Tag(name = "Delivery Status", description = "APIs for managing voter card deliveries")
@CrossOrigin(origins = "http://localhost:3000")
public class DeliveryStatusController {

    @Autowired
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    private VoterCardApplicationDAO applicationDAO;

    @Autowired
    private DeliveryStatusDAO deliveryStatusDAO;


    @PostMapping("/create")
    public ResponseEntity<?> createDelivery(@RequestBody Map<String, Long> request) {
        Long applicationId = request.get("applicationId");

        if (applicationId == null) {
            return ResponseEntity.badRequest().body("Application ID is required.");
        }

        Optional<VoterCardApplication> applicationOptional = applicationDAO.findById(applicationId);
        if (applicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voter Card Application not found.");
        }

        if (deliveryStatusDAO.existsByVoterCardApplication_ApplicationId(applicationId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Delivery record already exists.");
        }

        DeliveryStatus newStatus = new DeliveryStatus();
        newStatus.setVoterCardApplication(applicationOptional.get());
        newStatus.setStatus(DeliveryStatusType.IN_PROGRESS);
        deliveryStatusDAO.save(newStatus);

        return ResponseEntity.status(HttpStatus.CREATED).body("Delivery status created successfully.");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("status");

        Optional<DeliveryStatus> optionalDelivery = deliveryStatusDAO.findById(id);
        if (optionalDelivery.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery not found.");
        }

        try {
            DeliveryStatus deliveryStatus = optionalDelivery.get();
            deliveryStatus.setStatus(DeliveryStatusType.valueOf(newStatus));
            deliveryStatus.setUpdatedAt(LocalDateTime.now());

            deliveryStatusDAO.save(deliveryStatus);
            return ResponseEntity.ok("Status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value.");
        }
    }
    

    @GetMapping("/{deliveryId}")
    public ResponseEntity<?> getDeliveryStatus(@PathVariable Long deliveryId) {
        Optional<DeliveryStatus> deliveryOptional = deliveryStatusDAO.findById(deliveryId);

        if (deliveryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery status not found.");
        }

        return ResponseEntity.ok(new DeliveryStatusDTO(deliveryOptional.get()));
    }
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get applications by user ID")
    public ResponseEntity<?> getApplicationsByUserId(@PathVariable Long userId) {

        Optional<VoterCardApplication> applicationOptional = applicationDAO.findByUserUserId(userId);
        if (applicationOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("applicationId", applicationOptional.get().getApplicationId())); 
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No application found.");
    }


    @GetMapping
    public ResponseEntity<List<DeliveryStatusDTO>> getAllDeliveries() {
        List<DeliveryStatusDTO> deliveries = deliveryStatusService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeliveryStatusDTO>> getDeliveriesByStatus(@PathVariable String status) {
        try {
            List<DeliveryStatusDTO> deliveries = deliveryStatusService.getDeliveriesByStatus(status);
            return ResponseEntity.ok(deliveries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/voterid/{userId}")
    public ResponseEntity<?> getVoterId(@PathVariable Long userId) {
        Optional<VoterCardApplication> applicationOptional = applicationDAO.findByUserUserId(userId);

        if (applicationOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("No application found for this user.");
        }

        VoterCardApplication application = applicationOptional.get();

        if (!application.getStatus().equals(ApplicationStatus.Approved)) {
            return ResponseEntity.badRequest().body("Application is not approved yet.");
        }

        return ResponseEntity.ok("VOTER-" + application.getApplicationId());
    }
    
}
