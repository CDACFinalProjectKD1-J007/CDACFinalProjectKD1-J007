package com.electioncommission.pojos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.electioncommission.enums.ComplaintStatus;
import com.electioncommission.enums.ComplaintType;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
public class Complaints extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voter_id", nullable = false) 	
    private User voter;
	@Enumerated(EnumType.STRING)
    @Column(name="complaint_type")
    private ComplaintType complaintType;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name="complaint_status")
    private ComplaintStatus complaintStatus;
    
    private boolean status;

	@PrePersist
	public void prePersist() {
		this.status = true;
	}
}

