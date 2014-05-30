package com.eulersbridge.iEngage.rest.domain;

public class Response 
{
	public boolean success;
	String errorReason;
	
	public Response(boolean success,String errorReason)
	{
		this.success=success;
		this.errorReason=errorReason;
	}
	
	public boolean getSuccess()
	{
		return success;
	}
	
	public String getErrorReason()
	{
		return errorReason;
	}
}
