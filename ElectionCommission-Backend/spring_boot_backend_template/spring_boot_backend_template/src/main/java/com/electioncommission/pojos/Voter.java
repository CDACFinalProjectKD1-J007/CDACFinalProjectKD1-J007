package com.electioncommission.pojos;

import java.util.Date;

import com.electioncommission.enums.Gender;
import com.electioncommission.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "voters")
@Getter
@Setter
@NoArgsConstructor
public class Voter extends BaseEntity {
    private String address;
    private String district;
    private Date dob;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String state;
    @Enumerated(EnumType.STRING)
    private Status status;
}
