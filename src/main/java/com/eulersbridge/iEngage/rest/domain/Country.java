package com.eulersbridge.iEngage.rest.domain;

public class Country 
{
	int countryId;
	String countryName;
	Institution universities[];
	
	public Country(int id,String name,Institution unis[])
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
	
	public Institution[] getUniversities()
	{
		return universities;
	}
}
