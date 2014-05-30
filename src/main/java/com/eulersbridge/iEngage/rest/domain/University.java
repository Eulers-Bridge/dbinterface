package com.eulersbridge.iEngage.rest.domain;

public class University 
{
	int universityId;
	String universityName;
	
	public University(int id,String uni)
	{
		universityId=id;
		universityName=uni;
	}
	
	public int getUniversityId()
	{
		return universityId;
	}
	
	public String getUniversityName()
	{
		return universityName;
	}
	

}
