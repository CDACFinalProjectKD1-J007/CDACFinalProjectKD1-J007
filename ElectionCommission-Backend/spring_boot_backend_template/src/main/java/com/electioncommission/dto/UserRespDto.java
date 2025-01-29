package com.electioncommission.dto;

import java.sql.Date;

import com.electioncommission.pojos.Gender;
import com.electioncommission.pojos.Role;
import com.electioncommission.pojos.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class UserRespDto {
	
	private String firstName;
	private String lastName;
	private Gender gender;
	private Role role;
	private String phone;
	private String email;
	private String address;
	private String state;
	private String district;
	private Date dob;
	private Status status;
}
