package com.electioncommission.dto;

import com.electioncommission.enums.ComplaintStatus;
import com.electioncommission.enums.ComplaintType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintReqDto {

    private ComplaintType complaintType;
    private String description;
    private ComplaintStatus complaintStatus;
}
