package com.eulersbridge.iEngage.database.domain;

public class Institution 
{
	String name;
	String campus;
	String state;
	String country;
	
	public Institution()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getCampus()
	{
		return campus;
	}
	public void setCampus(String campus)
	{
		this.campus=campus;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state=state;
	}
	public String getCountry()
	{
		return country;
	}
	public void setCountry(String country)
	{
		this.country=country;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ name = ");
		buff.append(name);
		buff.append(", campus = ");
		buff.append(campus);
		buff.append(", state = ");
		buff.append(state);
		buff.append(", country = ");
		buff.append(country);
		buff.append(" ]");
		return buff.toString();
	}
}
