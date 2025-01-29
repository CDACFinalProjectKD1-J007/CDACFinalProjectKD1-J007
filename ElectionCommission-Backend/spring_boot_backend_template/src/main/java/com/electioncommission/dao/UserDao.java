package com.electioncommission.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electioncommission.pojos.User;

public interface UserDao extends JpaRepository<User, Long> {

}

