	package com.Voterid.exception;
	
	public class UserNotFoundException extends RuntimeException {
	
		 private static final long serialVersionUID = 1L;
	    // Constructor with email
	    public UserNotFoundException(String email) {
	        super("User not found with email: " + email);
	    }
	    
	}
