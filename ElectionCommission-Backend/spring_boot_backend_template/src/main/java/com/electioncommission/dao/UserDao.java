package com.electioncommission.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electioncommission.pojos.Voter;

public interface UserDao extends JpaRepository<Voter, Long> {

}

