package com.eulersbridge.iEngage.rest.controller.fixture;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.rest.domain.User;

public class RestDataFixture 
{
	
	public static final String USER_ITEM = "user1";
	
	public static User uniMelbUser()
	{
		User user = new User ();
		return user;
	}
	public static UserDetails customEmailUser(String email)
	{
		UserDetails user=new UserDetails(email);
		return user;
	}
	public static ReadUserEvent customEmailUser2(String email)
	{
		UserDetails user=new UserDetails(email);
		user.setFirstName("Greg");
		user.setLastName("Newitt");
		user.setGender("Male");
		user.setNationality("Australian");
		user.setPersonality("None");
		user.setYearOfBirth("1971");
		user.setPassword("password");
//		user.setUserItems(Collections.singletonMap(USER_ITEM, 12));
		return new ReadUserEvent(email,user);
	}
}
