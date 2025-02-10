package com.Voterid.controller;

import com.Voterid.dao.UserDAO;
import com.Voterid.dao.VoterCardApplicationDAO;
import com.Voterid.dto.VoterCardApplicationDTO;
import com.Voterid.pojo.User;
import com.Voterid.pojo.VoterCardApplication;
import com.Voterid.services.VoterCardApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@Tag(name = "Voter Card Applications", description = "APIs for managing voter card applications")
public class VoterCardApplicationController {
    
    @Autowired
    private VoterCardApplicationService applicationService;
    
    @Autowired
    private VoterCardApplicationDAO applicationDAO;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/status/{status}")
    public ResponseEntity<List<VoterCardApplicationDTO>> getApplicationsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
    }
    

    @PutMapping("/{id}/status")
    public ResponseEntity<VoterCardApplicationDTO> updateApplicationStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        VoterCardApplicationDTO updatedApplication = applicationService.updateApplicationStatus(id, newStatus);
        return ResponseEntity.ok(updatedApplication);
    }

    @GetMapping
    @Operation(summary = "Get all applications")
    public ResponseEntity<List<VoterCardApplicationDTO>> getAllApplications() {
        List<VoterCardApplicationDTO> dtos = applicationService.getAllApplications()
                .stream()
                .map(VoterCardApplicationDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get application by ID")
    public ResponseEntity<VoterCardApplicationDTO> getApplicationById(@PathVariable Long id) {
        VoterCardApplication application = applicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new VoterCardApplicationDTO(application));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get applications by user ID")
    public ResponseEntity<?> getApplicationsByUserId(@PathVariable Long userId) {

        List<VoterCardApplicationDTO> applications = applicationService.getApplicationsByUserId(userId)
            .stream()
            .map(VoterCardApplicationDTO::new)
            .collect(Collectors.toList());

        if (!applications.isEmpty()) {
            return ResponseEntity.ok(applications);
        }

        Optional<VoterCardApplication> applicationOptional = applicationDAO.findByUserUserId(userId);
        if (applicationOptional.isPresent()) {
            return ResponseEntity.ok(new VoterCardApplicationDTO(applicationOptional.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No application found.");
    }

   

    @PostMapping 
    public ResponseEntity<?> createApplication(@RequestBody VoterCardApplication application) {
        if (application.getUser() == null || application.getUser().getUserId() == null) {
            return ResponseEntity.badRequest().body("User is required.");
        }

        
        Optional<User> userOptional = userDAO.findById(application.getUser().getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        application.setUser(userOptional.get());  

        VoterCardApplication savedApplication = applicationDAO.save(application);
        return ResponseEntity.ok(savedApplication);
    }

    @PutMapping("/user/{userId}/voter-id")
    public ResponseEntity<?> updateVoterId(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String voterId = request.get("voterId");  

        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = userOptional.get();
        user.setVoterNumber(voterId);  
        userDAO.save(user);

        return ResponseEntity.ok("Voter ID updated successfully!");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing application")
    public ResponseEntity<VoterCardApplicationDTO> updateApplication(@PathVariable Long id, 
                                                                     @RequestBody VoterCardApplicationDTO applicationDTO) {
        VoterCardApplication updatedApplication = applicationService.updateApplication(id, applicationDTO.toEntity());
        return ResponseEntity.ok(new VoterCardApplicationDTO(updatedApplication));
    }
}
