package com.electioncommission.dto;

import java.sql.Date;

import com.electioncommission.pojos.Gender;
import com.electioncommission.pojos.Role;

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
	private String email;
	private String phone;
	private Gender gender;
	private String address;
	private String district;
	private String state;
	private Date dob;
	private Role role;
}
