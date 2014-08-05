package com.eulersbridge.iEngage.database.domain.Fixture;

import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;

public class DatabaseDataFixture 
{
	public static User populateUserGnewitt()
	{
		Long nodeId=(long)1;
		boolean verified=true;
		Institution inst=populateInstUniMelb();
		return populateUser("gnewitt@hotmail.com", "Greg", "Newitt", "Male", "Australian", "1971", "None", "password", nodeId, verified, inst);
	}
	public static User populateUserGnewitt2()
	{
		Long nodeId=(long)2;
		boolean verified=true;
		Institution inst=populateInstUniMelb();
		return populateUser("greg.newitt@unimelb.edu.au", "Greg", "Newitt", "Male", "Australian", "1971", "None", "password", nodeId, verified, inst);
	}
	public static User populateUser(String email, String firstName, String lastName, String gender, String nationality, String yearOfBirth, String personality, String password, Long id, boolean verified, Institution inst)
	{
		User user=new User(email, firstName, lastName, gender, nationality, yearOfBirth, personality, password);
		Long nodeId=id;
		user.setNodeId(nodeId);
		user.setAccountVerified(verified);
		user.setInstitution(inst);
		return user;
	}
	public static Institution populateInstUniMelb()
	{
		Country country=populateCountryAust();
		return populateInst("University of Melbourne", "Parkville", "Victoria", country);
	}
	
	public static Institution populateInst(String name,String campus,String state,Country country)
	{
		Institution inst=new Institution(name, campus, state, country);
		return inst;
	}
	
	public static Country populateCountryAust()
	{
		return populateCountry((long)1,"Australia");
	}
	
	public static Country populateCountry(Long nodeId,String countryName)
	{
		Country country=new Country();
		country.setCountryName(countryName);
		country.setNodeId(nodeId);
		return country;
	}

}
