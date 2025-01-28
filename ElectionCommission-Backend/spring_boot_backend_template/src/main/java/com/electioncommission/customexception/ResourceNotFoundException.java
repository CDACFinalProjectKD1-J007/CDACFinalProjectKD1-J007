package com.electioncommission.customexception;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String errMesg)
	{
		super(errMesg);
	}
}
