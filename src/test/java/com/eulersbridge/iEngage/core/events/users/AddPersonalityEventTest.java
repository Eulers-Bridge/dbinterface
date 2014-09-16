/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class AddPersonalityEventTest 
{
	String email;
	PersonalityDetails details;
	Long nodeId=1L;
	float extroversion=3.4F;
	float agreeableness=3.6F;
	float conscientiousness=4.8F;
	float emotionalStability=2.9F;
	float openness=4.2F;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		details=new PersonalityDetails(nodeId, extroversion, agreeableness, conscientiousness, emotionalStability, openness);
		email="greg.newitt@unimelb.edu.au";
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent#AddPersonalityEvent(java.lang.String, com.eulersbridge.iEngage.core.events.users.PersonalityDetails)}.
	 */
	@Test
	public void testAddPersonalityEvent() 
	{
		AddPersonalityEvent addEvt=new AddPersonalityEvent(email, details);
		assertNotNull("Not yet implemented",addEvt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent#getEmail()}.
	 */
	@Test
	public void testGetEmail() 
	{
		AddPersonalityEvent addEvt=new AddPersonalityEvent(email, details);
		assertEquals("Email values do not match.",addEvt.getEmail(),email);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent#setEmail(java.lang.String)}.
	 */
	@Test
	public void testSetEmail() 
	{
		AddPersonalityEvent addEvt=new AddPersonalityEvent(email, details);
		String otherAddress="test@isegoria.com";
		addEvt.setEmail(otherAddress);
		assertEquals("Email values do not match.",addEvt.getEmail(),otherAddress);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent#getDetails()}.
	 */
	@Test
	public void testGetDetails() 
	{
		AddPersonalityEvent addEvt=new AddPersonalityEvent(email, details);
		PersonalityDetails dets=addEvt.getDetails();
		assertEquals("agreeableness values do not match.",dets.getAgreeableness(),agreeableness,0);
		assertEquals("extroversion values do not match.",dets.getExtroversion(),extroversion,0);
		assertEquals("emotional Stability values do not match.",dets.getEmotionalStability(),emotionalStability,0);
		assertEquals("openess values do not match.",dets.getOpeness(),openness,0);
		assertEquals("conscientiousness values do not match.",dets.getConscientiousness(),conscientiousness,0);
		assertEquals("NodeId does not match.",dets.getPersonalityId(),nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent#setDetails(com.eulersbridge.iEngage.core.events.users.PersonalityDetails)}.
	 */
	@Test
	public void testSetDetails() 
	{
		AddPersonalityEvent addEvt=new AddPersonalityEvent(email, null);
		addEvt.setDetails(details);
		PersonalityDetails dets=addEvt.getDetails();
		assertEquals("agreeableness values do not match.",dets.getAgreeableness(),agreeableness,0);
		assertEquals("extroversion values do not match.",dets.getExtroversion(),extroversion,0);
		assertEquals("emotional Stability values do not match.",dets.getEmotionalStability(),emotionalStability,0);
		assertEquals("openess values do not match.",dets.getOpeness(),openness,0);
		assertEquals("conscientiousness values do not match.",dets.getConscientiousness(),conscientiousness,0);
		assertEquals("NodeId does not match.",dets.getPersonalityId(),nodeId);
	}

}
