package com.Voterid.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN, VOTER;
	@JsonCreator
    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) { 
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
	   @JsonValue
       public String toLowerCase() {
           return this.name().toLowerCase(); 
       }
   
   }