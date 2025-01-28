package com.electioncommission.pojos;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="voter")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Voter extends BaseEntity {
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column(name="phone_number")
	private String phone;
	private String address;
	private String state;
	private String district;
	private Date dob;
	@Enumerated(EnumType.STRING)
	private Status status;
}
