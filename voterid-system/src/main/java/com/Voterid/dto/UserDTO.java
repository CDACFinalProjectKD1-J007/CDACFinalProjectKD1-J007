package com.Voterid.dto;
import com.Voterid.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  
public class UserDTO {
	    private Long userId;
	    private String name;
	    private String email;
	    private Role role;
	  

	}

