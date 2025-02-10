package com.Voterid.dao;

import com.Voterid.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByVoterNumber(String voterNumber);
    Optional<User> findByVoterNumber(String voterNumber);
}