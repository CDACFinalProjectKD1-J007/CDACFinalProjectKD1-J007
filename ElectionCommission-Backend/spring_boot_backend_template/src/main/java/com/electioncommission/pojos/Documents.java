package com.electioncommission.pojos;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
public class Documents extends BaseEntity{

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Column(name = "aadhar_card_url", length = 255)
	private String aadharCardUrl;

	@Column(name = "pan_card_url", length = 255)
	private String panCardUrl;

	@Column(name = "birth_certificate_url", length = 255)
	private String birthCertificateUrl;

	@Column(name = "a4_photo_url", length = 255)
	private String a4PhotoUrl;
}