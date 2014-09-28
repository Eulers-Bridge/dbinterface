/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.junit.Assert.*;

import org.springframework.mock.web.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;

/**
 * @author Greg Newitt
 *
 */
public class PersonalityTest 
{

	private static final float delta = 0;
	private float emotionalStability=3.4F;
	private float agreeableness=4.3F;
	private float openess=4.1F;
	private float conscientiousness=3.8F;
	private float extroversion=2.5F;
	
	@Mock
	private ServletRequestAttributes attrs;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request=new MockHttpServletRequest();
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#Personality(float, float, float, float, float)}.
	 */
	@Test
	public void testPersonality() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotNull("Not yet implemented",personality);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#getExtroversion()}.
	 */
	@Test
	public void testGetExtroversion() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Extroversion does not match expected value.", personality.getExtroversion(), extroversion, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#getAgreeableness()}.
	 */
	@Test
	public void testGetAgreeableness() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Agreeableness does not match expected value.", personality.getAgreeableness(), agreeableness, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#getConscientiousness()}.
	 */
	@Test
	public void testGetConscientiousness() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Conscientiousness does not match expected value.", personality.getConscientiousness(), conscientiousness, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#getEmotionalStability()}.
	 */
	@Test
	public void testGetEmotionalStability() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Emotional Stability does not match expected value.", personality.getEmotionalStability(), emotionalStability, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#getOpeness()}.
	 */
	@Test
	public void testGetOpeness() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Openess does not match expected value.", personality.getOpeness(), openess, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#getPersonalityId()}.
	 */
	@Test
	public void testGetPersonalityId() 
	{
		Long nodeId=2L;
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		personality.setPersonalityId(nodeId);
		assertEquals("NodeId does not match expected value.", personality.getPersonalityId(), nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#setPersonalityId(java.lang.Long)}.
	 */
	@Test
	public void testSetPersonalityId() 
	{
		Long nodeId=2L;
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		personality.setPersonalityId(nodeId);
		assertEquals("NodeId does not match expected value.", personality.getPersonalityId(), nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#toPersonalityDetails()}.
	 */
	@Test
	public void testToPersonalityDetails() 
	{
		Long nodeId=2L;
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		personality.setPersonalityId(nodeId);
		PersonalityDetails dets=personality.toPersonalityDetails();
		assertEquals("",personality.getPersonalityId(),dets.getPersonalityId());
		assertEquals("",personality.getExtroversion(),dets.getExtroversion(),0);
		assertEquals("",personality.getAgreeableness(),dets.getAgreeableness(),0);
		assertEquals("",personality.getConscientiousness(),dets.getConscientiousness(),0);
		assertEquals("",personality.getEmotionalStability(),dets.getEmotionalStability(),0);
		assertEquals("",personality.getOpeness(),dets.getOpeness(),0);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Personality#fromPersonalityDetails(com.eulersbridge.iEngage.core.events.users.PersonalityDetails)}.
	 */
	@Test
	public void testFromPersonalityDetails() 
	{
		Long nodeId=2L;
		PersonalityDetails dets=new PersonalityDetails(nodeId, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		Personality personality=Personality.fromPersonalityDetails(dets);
		assertEquals("",personality.getPersonalityId(),dets.getPersonalityId());
		assertEquals("",personality.getExtroversion(),dets.getExtroversion(),0);
		assertEquals("",personality.getAgreeableness(),dets.getAgreeableness(),0);
		assertEquals("",personality.getConscientiousness(),dets.getConscientiousness(),0);
		assertEquals("",personality.getEmotionalStability(),dets.getEmotionalStability(),0);
		assertEquals("",personality.getOpeness(),dets.getOpeness(),0);
	}

}
