package com.Voterid.dao;

import com.Voterid.pojo.User;
import com.Voterid.pojo.VoterCardApplication;
import com.Voterid.enums.ApplicationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VoterCardApplicationDAO extends JpaRepository<VoterCardApplication, Long> {
    List<VoterCardApplication> findByUser_UserId(Long userId);
    List<VoterCardApplication> findByStatus(ApplicationStatus status);
    boolean existsByUser(User user);
    Optional<VoterCardApplication> findByUserUserId(Long userId);
}