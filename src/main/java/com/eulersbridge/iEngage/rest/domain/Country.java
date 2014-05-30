package com.eulersbridge.iEngage.rest.domain;

public class Country 
{
	int countryId;
	String countryName;
	University universities[];
	
	public Country(int id,String name,University unis[])
	{
		countryId=id;
		countryName=name;
		universities=unis;
	}
	
	public int getCountryId()
	{
		return countryId;
	}
	
	public String getCountryName()
	{
		return countryName;
	}
	
	public University[] getUniversities()
	{
		return universities;
	}
}
