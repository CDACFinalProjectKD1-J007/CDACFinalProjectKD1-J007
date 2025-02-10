package com.electioncommission.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electioncommission.pojos.Complaints;

public interface ComplaintDao extends JpaRepository<Complaints, Long> {

}
