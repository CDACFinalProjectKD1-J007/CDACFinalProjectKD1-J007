package com.Voterid.controller;

import com.Voterid.pojo.Complaint;
import com.Voterid.enums.ComplaintStatus;
import com.Voterid.dto.Complaint_ManagementDTO;
import com.Voterid.services.impl.ComplaintmanagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/complaints_management")
@Tag(name = "Complaint Management", description = "APIs for managing complaints")
public class ComplaintManagementController {

    @Autowired
    private ComplaintmanagementServiceImpl complaintService;

    @GetMapping
    public ResponseEntity<List<Complaint_ManagementDTO>> getAllComplaints() {
        List<Complaint_ManagementDTO> complaintDTOs = complaintService.getAllComplaints()
                                                .stream()
                                                .map(Complaint_ManagementDTO::new)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(complaintDTOs);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing complaint")
    public ResponseEntity<?> updateComplaint(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Complaint complaint = complaintService.getComplaintById(id);
        if (complaint == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found with ID: " + id);
        }

        if (updates.containsKey("status")) {
            try {
                String newStatus = updates.get("status").toString().toUpperCase();
                complaint.setStatus(ComplaintStatus.valueOf(newStatus));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid complaint status.");
            }
        }

        Complaint updatedComplaint = complaintService.updateComplaint(id, complaint);
        return ResponseEntity.ok(new Complaint_ManagementDTO(updatedComplaint));
    }
}
