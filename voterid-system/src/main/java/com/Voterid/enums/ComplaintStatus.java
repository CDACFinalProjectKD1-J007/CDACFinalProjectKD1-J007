package com.Voterid.enums;

public enum ComplaintStatus {
	FORWARDED, COMPLETED; 

    public static ComplaintStatus fromString(String status) {
        for (ComplaintStatus cs : ComplaintStatus.values()) {
            if (cs.name().equalsIgnoreCase(status)) { 
                return cs;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}
