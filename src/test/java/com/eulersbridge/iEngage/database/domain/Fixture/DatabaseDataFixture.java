package com.eulersbridge.iEngage.database.domain.Fixture;

import java.util.Calendar;
import java.util.HashMap;

import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.StudentYear;
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
	public static HashMap<Long,User> populateUsers()
	{
		HashMap<Long, User> users=new HashMap<Long, User>();
		User initialUser=populateUserGnewitt();
		users.put(new Long(1), initialUser);
		return users;
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
	
	public static StudentYear populateStudentYear2014()
	{
		return populateStudentYear((long)1,"2014");
	}
	
	public static StudentYear populateStudentYear(Long nodeId,String theYear)
	{
		StudentYear year=new StudentYear();
		year.setYear(theYear);
		Calendar now=Calendar.getInstance();
		year.setStart(now.getTimeInMillis());
		year.setEnd(now.getTimeInMillis()+200000);
		year.setInstitution(populateInstUniMelb());
		year.setNodeId(nodeId);
		return year;
	}
	
	public static HashMap<Long,Country> populateCountries()
	{
		HashMap<Long, Country> countrys=new HashMap<Long, Country>();
		Country initialCountry=populateCountryAust();
		countrys.put(new Long(1), initialCountry);
		return countrys;
	}
	
	public static HashMap<Long,Institution> populateInstitutions()
	{
		HashMap<Long, Institution> institutions=new HashMap<Long, Institution>();
		Institution initialInst=populateInstUniMelb();
		Long nodeId=new Long(1);
		initialInst.setNodeId(nodeId);
		institutions.put(nodeId, initialInst);
		return institutions;
	}
	
	public static HashMap<Long,StudentYear> populateStudentYears()
	{
		HashMap<Long, StudentYear> years=new HashMap<Long, StudentYear>();
		StudentYear initialYear=populateStudentYear2014();
		years.put(new Long(1), initialYear);
		return years;
	}

}
