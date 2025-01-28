package com.electioncommission.services;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electioncommission.customexception.ApiException;
import com.electioncommission.dao.UserDao;
import com.electioncommission.dto.UserAuthDto;
import com.electioncommission.dto.UserReqDto;
import com.electioncommission.pojos.Voter;

//import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String RegisterUser(UserReqDto user) {
		Voter v=modelMapper.map(user, Voter.class);
		Voter persistentUser = userDao.save(v);
		return "Added New Category";
	}


}
