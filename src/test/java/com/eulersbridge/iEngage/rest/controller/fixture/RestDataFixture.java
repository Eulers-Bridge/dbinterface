package com.eulersbridge.iEngage.rest.controller.fixture;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.rest.domain.User;

public class RestDataFixture 
{
	public static User uniMelbUser()
	{
		User user = populateUser();
		return user;
	}
	public static UserDetails customEmailUser(String email)
	{
		UserDetails userDetails=populateUser().toUserDetails();
		userDetails.setEmail(email);
		return userDetails;
	}
	
	public static UserDetails customEmailUser()
	{
		UserDetails userDetails=populateUser().toUserDetails();
		return userDetails;
	}
	
	public static ReadUserEvent customEmailUser2(String email)
	{
		User user=populateUser();
		user.setEmail(email);
		return new ReadUserEvent(email,user.toUserDetails());
	}
	
	public static ReadUserEvent customEmailUser2()
	{
		UserDetails userDetails=populateUser().toUserDetails();
		return new ReadUserEvent(userDetails.getEmail(),userDetails);
	}
	
	public static User populateUser()
	{
		User user=new User();
		user.setEmail("gnewitt@hotmail.com");
		user.setGivenName("Greg");
		user.setFamilyName("Newitt");
		user.setGender("Male");
		user.setNationality("Australian");
		user.setYearOfBirth("1971");
		user.setPassword("password");
		user.setContactNumber("0400432123");
		user.setAccountVerified(false);
		user.setInstitutionId(new Long(26));
		
		return user;
	}
}
