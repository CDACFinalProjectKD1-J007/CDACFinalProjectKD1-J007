package com.electioncommission.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electioncommission.dao.ComplaintDao;
import com.electioncommission.dao.FaqDao;
import com.electioncommission.dao.UserDao;
import com.electioncommission.dto.ApiResponse;
import com.electioncommission.dto.ComplaintReqDto;
import com.electioncommission.dto.ComplaintRespDto;
import com.electioncommission.dto.FaqReqDto;
import com.electioncommission.enums.Role;
import com.electioncommission.pojos.Complaints;
import com.electioncommission.pojos.Faq;
import com.electioncommission.pojos.User;

@Service
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

	@Autowired
    private ComplaintDao complaintDao;
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
		@Override
		public ApiResponse addNewComplaint(ComplaintReqDto dto, Long voterId) {
			User user = userDao.findById(voterId).orElseThrow(() -> new RuntimeException("Invalid Id"));
			if (user.getRole().equals(Role.Voter)) {
				Complaints complaint = modelMapper.map(dto, Complaints.class);
				complaint.setVoter(user);
				Complaints persistentComplaint = complaintDao.save(complaint);
				return new ApiResponse("new complaint added with id=" + persistentComplaint.getId());
			}
			return new ApiResponse("complaint not added");

		}

		@Override
		public List<ComplaintRespDto> getAllComplaints() {
			return complaintDao.findAll().stream().filter(complaint -> complaint.isStatus())
					.map(complaint -> modelMapper.map(complaint, ComplaintRespDto.class)).collect(Collectors.toList());
		}

		@Override
		public ApiResponse deleteComplaint(Long id, Long vId) {
			User user = userDao.findById(vId).orElseThrow(() -> new RuntimeException("Invalid Id"));
			if (user.getRole().equals(Role.Voter)) {
				Complaints complaint = complaintDao.findById(id)
						.orElseThrow(() -> new RuntimeException("Invalid Complaint Id"));
				complaint.setStatus(false);
				complaintDao.save(complaint);
				return new ApiResponse("Complaint deleted");
			}
			return new ApiResponse("Complaint not deleted");
		}

	
	}


