package com.electioncommission.dto;

import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaqReqDto {
	private String question;

	private String answer;
}
