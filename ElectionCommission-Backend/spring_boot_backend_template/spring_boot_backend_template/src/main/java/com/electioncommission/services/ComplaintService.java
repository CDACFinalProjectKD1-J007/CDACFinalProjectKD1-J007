package com.electioncommission.services;

import java.util.List;

import com.electioncommission.dto.ApiResponse;
import com.electioncommission.dto.ComplaintReqDto;
import com.electioncommission.dto.ComplaintRespDto;

public interface ComplaintService {
	ApiResponse addNewComplaint(ComplaintReqDto dto, Long voterId);
	List<ComplaintRespDto> getAllComplaints();

    ApiResponse deleteComplaint(Long id, Long vId);
	
//	ApiResponse updateComplaint(ComplaintReqDto dto,Long id, Long vid);
}
