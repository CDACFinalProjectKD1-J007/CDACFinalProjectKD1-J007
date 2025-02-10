package com.electioncommission.services;

import com.electioncommission.dto.UserAuthDto;
import com.electioncommission.dto.UserReqDto;
import com.electioncommission.dto.UserRespDto;

public interface UserService {
	UserRespDto LoginUser(UserAuthDto user); 
	String RegisterUser(UserReqDto user);
}
