package com.eulersbridge.iEngage.core.events.users;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class UpdateUserEventTest
{
	final String email = new String("yikaig@gmail.com");
	final String givenName = new String("Yikai");
	final String familyName = new String("Gong");
	final String gender = new String("male");
	final String nationality = new String("China");
	final String yearOfBirth = new String("1989");
	final String password = new String("password");
	final Long institutionId = new Long(1);
	UserDetails userDetails = null;
	UpdateUserEvent updateUserEvent = null;

	@Before
	public void setUp() throws Exception
	{
		userDetails = new UserDetails(email);
		userDetails.setGivenName(givenName);
		userDetails.setFamilyName(familyName);
		userDetails.setGender(gender);
		userDetails.setNationality(nationality);
		userDetails.setYearOfBirth(yearOfBirth);
		userDetails.setPassword(password);
		userDetails.setInstitutionId(institutionId);
		updateUserEvent = new UpdateUserEvent(email, userDetails);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testUpdateUserEvent() throws Exception
	{
		assertNotNull("updateUserEvent is null");
	}

	@Test
	public void testGetEmail() throws Exception
	{
		assertEquals("email does not match", email, updateUserEvent.getEmail());
	}

	@Test
	public void testGetUserDetails() throws Exception
	{
		assertEquals("userDetails does not match", userDetails,
				updateUserEvent.getDetails());
	}
}