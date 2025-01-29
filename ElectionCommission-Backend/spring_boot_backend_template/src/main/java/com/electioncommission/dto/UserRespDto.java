package com.electioncommission.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.electioncommission.enums.Gender;
import com.electioncommission.enums.Role;
import com.electioncommission.enums.Status;
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
