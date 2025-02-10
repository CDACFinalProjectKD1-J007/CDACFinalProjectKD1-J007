package com.Voterid.controller;

import com.Voterid.pojo.Complaint;
import com.Voterid.dto.ComplaintDTO;
import com.Voterid.pojo.User;
import com.Voterid.enums.ComplaintStatus;
import com.Voterid.services.impl.ComplaintServiceImpl;
import com.Voterid.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/complaints")
@Tag(name = "Complaint Management", description = "APIs for managing complaints")
public class ComplaintController {

    @Autowired
    private ComplaintServiceImpl complaintService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get complaints by user ID")
    public ResponseEntity<List<ComplaintDTO>> getComplaintsByUserId(@PathVariable Long userId) {
        List<ComplaintDTO> complaints = complaintService.getComplaintsByUserId(userId)
                                               .stream()
                                               .map(ComplaintDTO::new)
                                               .toList();
        return ResponseEntity.ok(complaints.isEmpty() ? Collections.emptyList() : complaints);
    }

    @GetMapping
    @Operation(summary = "Get all complaints")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints() {
        List<ComplaintDTO> complaintDTOs = complaintService.getAllComplaints()
                                               .stream()
                                               .map(ComplaintDTO::new)
                                               .toList();
        return ResponseEntity.ok(complaintDTOs);
    }

    @PostMapping
    @Operation(summary = "Create a new complaint")
    public ResponseEntity<?> createComplaint(@RequestBody Map<String, Object> payload) {
        System.out.println("Received Complaint Data: " + payload); // Debugging log

        if (!payload.containsKey("userId") || !(payload.get("userId") instanceof Number)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must be provided.");
        }

        Long userId = ((Number) payload.get("userId")).longValue();
        String complaintType = payload.get("complaintType") instanceof String ? (String) payload.get("complaintType") : null;
        String description = payload.get("description") instanceof String ? (String) payload.get("description") : null;

        if (complaintType == null || description == null || complaintType.isBlank() || description.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Complaint Type and Description are required.");
        }

        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("User not found with ID: " + userId);
        }

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setComplaintType(complaintType);
        complaint.setDescription(description);
        complaint.setStatus(ComplaintStatus.FORWARDED);

        Complaint savedComplaint = complaintService.createComplaint(complaint);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ComplaintDTO(savedComplaint));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid complaint ID.");
        }
        boolean isDeleted = complaintService.deleteComplaint(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint not found with ID: " + id);
        }
        return ResponseEntity.ok("Complaint deleted successfully.");
    }

   


}
