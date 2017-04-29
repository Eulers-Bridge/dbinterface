package com.eulersbridge.iEngage.core.events.users;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonalityAddedEventTest 
{

	private float emotionalStability=3.4F;
	private float agreeableness=4.3F;
	private float openess=4.1F;
	private float conscientiousness=3.8F;
	private float extroversion=2.5F;
	private float extroversion2=4.5F;
	private Long personalityId=2L;
	PersonalityAddedEvent evt;
	
	@Before
	public void setUp() throws Exception 
	{
		PersonalityDetails dets=new PersonalityDetails(personalityId, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		evt=new PersonalityAddedEvent();
		evt.setPersonalityDetails(dets);
	}

	@Test
	public final void testGetPersonalityDetails() 
	{
		PersonalityDetails dets=new PersonalityDetails(personalityId, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Details did not match expected.", dets,evt.getPersonalityDetails());
	}

	@Test
	public final void testSetPersonalityDetails() 
	{
		PersonalityDetails dets=new PersonalityDetails(personalityId, extroversion2, agreeableness, conscientiousness, emotionalStability, openess);
		evt.setPersonalityDetails(dets);
		assertEquals("Details did not match expected.", dets,evt.getPersonalityDetails());
	}

	@Test
	public final void testIsUserFound() 
	{
		assertTrue("User not found.",evt.isUserFound());
	}
	
	@Test
	public final void testSetUserFound() 
	{
		evt.setUserFound(false);
		assertFalse("User found.",evt.isUserFound());
		evt.setUserFound(true);
		assertTrue("User not found.",evt.isUserFound());
	}

	@Test
	public final void shouldReturnObjectWithUserNotFound()
	{
		PersonalityAddedEvent res=PersonalityAddedEvent.userNotFound();
		assertFalse("User is found...", res.isUserFound());
	}

}
