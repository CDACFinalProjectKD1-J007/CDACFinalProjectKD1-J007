package com.electioncommission.dto;

import java.sql.Date;

import com.electioncommission.enums.Gender;
import com.electioncommission.enums.Role;
import com.electioncommission.enums.Status;
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