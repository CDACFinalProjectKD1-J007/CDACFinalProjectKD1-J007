package com.electioncommission.services;

import com.electioncommission.dto.UserAuthDto;
import com.electioncommission.dto.UserReqDto;

public interface UserService {
	String RegisterUser(UserReqDto user);
}
