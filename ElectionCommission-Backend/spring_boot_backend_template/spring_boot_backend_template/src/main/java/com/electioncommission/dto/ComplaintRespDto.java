package com.electioncommission.dto;

import com.electioncommission.enums.ComplaintStatus;
import com.electioncommission.enums.ComplaintType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ComplaintRespDto {
	private ComplaintType complaintType;
    private String description;
    private ComplaintStatus complaintStatus;
}
