package com.electioncommission.dto;

import java.sql.Date;


import com.electioncommission.pojos.Gender;
import com.electioncommission.pojos.Role;
import com.electioncommission.pojos.Status;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReqDto {
	private String firstName;
	private String lastName;
	private Gender gender;
	private Role role;
	private String phone;
	private String vnumber;
	private String email;
	private String password;
	private String address;
	private String state;
	private String district;
	private Date dob;
	private Status status;
}