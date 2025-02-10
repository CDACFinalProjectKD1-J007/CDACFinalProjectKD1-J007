package com.electioncommission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electioncommission.dto.ApiResponse;
import com.electioncommission.dto.ComplaintReqDto;
import com.electioncommission.dto.ComplaintRespDto;
import com.electioncommission.services.ComplaintService;

@RestController
@RequestMapping("/Complaint")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class ComplaintController {
	
	@Autowired
	private ComplaintService complaintService;
	
	
	@PostMapping("/addComplaint/{voter_id}")
	public ResponseEntity<?> addNewComplaint(@PathVariable Long voter_id,
			@RequestBody ComplaintReqDto complaint) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(complaintService.addNewComplaint(complaint, voter_id));
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllComplaints() {
		List<ComplaintRespDto> complaint = complaintService.getAllComplaints();
		if (complaint.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(complaint);
	}
	
	@DeleteMapping("/deleteComplaint/{id}/{vId}")
	public ResponseEntity<?> deleteComplaints(@PathVariable Long id, @PathVariable Long vId) {
		try {
			return ResponseEntity.ok(complaintService.deleteComplaint(id, vId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}

}
