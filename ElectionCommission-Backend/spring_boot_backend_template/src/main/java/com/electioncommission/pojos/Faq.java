package com.electioncommission.pojos;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "faq")
@Getter
@Setter
@NoArgsConstructor
public class Faq extends BaseEntity {

	private String question;

	private String answer;
}
