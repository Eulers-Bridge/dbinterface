/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#Personality(float, float, float, float, float)}.
	 */
	@Test
	public void testPersonality() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotNull("Not yet implemented",personality);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getExtroversion()}.
	 */
	@Test
	public void testGetExtroversion() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Extroversion does not match expected value.", personality.getExtroversion(), extroversion, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getAgreeableness()}.
	 */
	@Test
	public void testGetAgreeableness() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Agreeableness does not match expected value.", personality.getAgreeableness(), agreeableness, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getConscientiousness()}.
	 */
	@Test
	public void testGetConscientiousness() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Conscientiousness does not match expected value.", personality.getConscientiousness(), conscientiousness, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getEmotionalStability()}.
	 */
	@Test
	public void testGetEmotionalStability() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Emotional Stability does not match expected value.", personality.getEmotionalStability(), emotionalStability, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getOpeness()}.
	 */
	@Test
	public void testGetOpeness() 
	{
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Openess does not match expected value.", personality.getOpeness(), openess, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getNodeId()}.
	 */
	@Test
	public void testGetNodeId() 
	{
		Long nodeId=2L;
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		personality.setNodeId(nodeId);
		assertEquals("NodeId does not match expected value.", personality.getNodeId(), nodeId);
	}
	
	@Test
	public void testToPersonalityDetails()
	{
		Long nodeId=2L;
		Personality personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		personality.setNodeId(nodeId);
		PersonalityDetails dets=personality.toPersonalityDetails();
		assertEquals("",personality.getNodeId(),dets.getPersonalityId());
		assertEquals("",personality.getExtroversion(),dets.getExtroversion(),0);
		assertEquals("",personality.getAgreeableness(),dets.getAgreeableness(),0);
		assertEquals("",personality.getConscientiousness(),dets.getConscientiousness(),0);
		assertEquals("",personality.getEmotionalStability(),dets.getEmotionalStability(),0);
		assertEquals("",personality.getOpeness(),dets.getOpeness(),0);
	}

	@Test
	public void testFromPersonalityDetails()
	{
		Long nodeId=2L;
		PersonalityDetails dets=new PersonalityDetails(nodeId, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		Personality personality=Personality.fromPersonalityDetails(dets);
		assertEquals("",personality.getNodeId(),dets.getPersonalityId());
		assertEquals("",personality.getExtroversion(),dets.getExtroversion(),0);
		assertEquals("",personality.getAgreeableness(),dets.getAgreeableness(),0);
		assertEquals("",personality.getConscientiousness(),dets.getConscientiousness(),0);
		assertEquals("",personality.getEmotionalStability(),dets.getEmotionalStability(),0);
		assertEquals("",personality.getOpeness(),dets.getOpeness(),0);
	}

}
