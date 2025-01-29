package com.electioncommission.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electioncommission.pojos.User;

public interface UserDao extends JpaRepository<User, Long> {
	Optional<User> findByEmailAndPassword(String email, String password);

}

