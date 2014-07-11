package com.eulersbridge.iEngage.rest.domain;

public class Institution 
{
	Long id;
	String name;
	String state;
	String country;
	
	public Institution()
	{
	}
	
	public Institution(Long id)
	{
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Institution(Long id,String name)
	{
		this.id=id;
		this.name=name;
	}
}
