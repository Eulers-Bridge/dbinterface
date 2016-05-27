package com.eulersbridge.iEngage.rest.domain;


public class Response 
{
	public boolean success;
	String errorReason;
	Object responseObject;
	
	public Response()
	{
		this.success=true;
	}
	
	public Response(boolean success)
	{
		this.success=success;
	}
	
	private Response(boolean success,String errorReason)
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
	
	public Object getResponseObject()
	{
		return responseObject;
	}

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public static Response failed(String errorReason)
	{
		Response result=new Response(false,errorReason);
		return result;
	}
}
