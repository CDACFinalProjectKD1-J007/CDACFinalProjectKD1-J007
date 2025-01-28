package com.electioncommission.dto;

import java.sql.Date;


import com.electioncommission.pojos.Gender;
import com.electioncommission.pojos.Status;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReqDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private Gender gender;
	private String address;
	private String district;
	private String state;
	private Date dob;
	private Status status;
}