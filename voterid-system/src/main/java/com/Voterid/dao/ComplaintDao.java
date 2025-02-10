package com.Voterid.dao;

import com.Voterid.pojo.Complaint;
import com.Voterid.enums.ComplaintStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintDao extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser_UserId(Long userId);
    List<Complaint> findByStatus(ComplaintStatus status);
}