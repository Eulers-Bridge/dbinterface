package com.eulersbridge.iEngage.rest.controller.fixture;

import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.domain.UserDomain;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class RestDataFixture 
{
	public static MappingJackson2HttpMessageConverter setUpConverter()
	{
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper mapper = new ObjectMapper();
		DeserializationFeature feature=DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
		mapper.enable(feature);
		converter.setObjectMapper(mapper);
		return converter;
	}

	public static String quoteNonNullStrings(String value)
	{
		if (value!=null) return '\"'+value+'\"';
		else return value;
	}
	
	public static UserDomain uniMelbUser()
	{
		UserDomain user = populateUser();
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
		UserDomain user=populateUser();
		user.setEmail(email);
		return new ReadUserEvent(email,user.toUserDetails());
	}
	
	public static ReadUserEvent customEmailUser2()
	{
		UserDetails userDetails=populateUser().toUserDetails();
		return new ReadUserEvent(userDetails.getEmail(),userDetails);
	}
	
	public static UserDomain populateUser()
	{
		UserDomain user=new UserDomain();
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
