package com.electioncommission.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electioncommission.customexception.ApiException;
//import jakarta.transaction.Transactional;
import com.electioncommission.dao.UserDao;
import com.electioncommission.dto.UserAuthDto;
import com.electioncommission.dto.UserReqDto;
import com.electioncommission.dto.UserRespDto;
import com.electioncommission.pojos.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String RegisterUser(UserReqDto user) {
		User u=modelMapper.map(user, User.class);
		User persistentUser = userDao.save(u);
		return "Added New Category";
	}
	
	@Override
	public UserRespDto LoginUser(UserAuthDto user) {
		User u = userDao.findByEmailAndPassword(user.getEmail(), user.getPassword())
				.orElseThrow(()->
				new ApiException("Invallid Email or Password"));
		
		return modelMapper.map(u, UserRespDto.class);
	}


}
