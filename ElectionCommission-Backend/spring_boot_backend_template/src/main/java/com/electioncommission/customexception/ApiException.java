package com.electioncommission.customexception;

public class ApiException extends RuntimeException{
	public ApiException(String errMesg)
	{
		super(errMesg);
	}
}

