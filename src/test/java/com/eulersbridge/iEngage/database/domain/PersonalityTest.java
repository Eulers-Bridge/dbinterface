/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
	Personality personality;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		personality=DatabaseDataFixture.populatePersonality1();
		emotionalStability=personality.getEmotionalStability();
		agreeableness=personality.getAgreeableness();
		openess=personality.getOpeness();
		conscientiousness=personality.getConscientiousness();
		extroversion=personality.getExtroversion();
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#Personality(float, float, float, float, float)}.
	 */
	@Test
	public void testPersonality() 
	{
		Personality personality=new Personality();
		assertNotNull("Not yet implemented",personality);
		personality=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotNull("Not yet implemented",personality);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getExtroversion()}.
	 */
	@Test
	public void testGetExtroversion() 
	{
		Personality personality=DatabaseDataFixture.populatePersonality1();
		assertEquals("Extroversion does not match expected value.", personality.getExtroversion(), extroversion, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getAgreeableness()}.
	 */
	@Test
	public void testGetAgreeableness() 
	{
		Personality personality=DatabaseDataFixture.populatePersonality1();
		assertEquals("Agreeableness does not match expected value.", personality.getAgreeableness(), agreeableness, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getConscientiousness()}.
	 */
	@Test
	public void testGetConscientiousness() 
	{
		Personality personality=DatabaseDataFixture.populatePersonality1();
		assertEquals("Conscientiousness does not match expected value.", personality.getConscientiousness(), conscientiousness, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getEmotionalStability()}.
	 */
	@Test
	public void testGetEmotionalStability() 
	{
		Personality personality=DatabaseDataFixture.populatePersonality1();
		assertEquals("Emotional Stability does not match expected value.", personality.getEmotionalStability(), emotionalStability, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getOpeness()}.
	 */
	@Test
	public void testGetOpeness() 
	{
		Personality personality=DatabaseDataFixture.populatePersonality1();
		assertEquals("Openess does not match expected value.", personality.getOpeness(), openess, delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#getNodeId()}.
	 */
	@Test
	public void testGetNodeId() 
	{
		Long nodeId=2L;
		Personality personality=DatabaseDataFixture.populatePersonality1();
		assertNotEquals("NodeId does not match expected value.", personality.getNodeId(), nodeId);
		personality.setNodeId(nodeId);
		assertEquals("NodeId does not match expected value.", personality.getNodeId(), nodeId);
	}
	
	@Test
	public void testToPersonalityDetails()
	{
		Long nodeId=2L;
		Personality personality=DatabaseDataFixture.populatePersonality1();
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
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Personality personalityTest=DatabaseDataFixture.populatePersonality1();
		assertEquals(personalityTest.hashCode(),personalityTest.hashCode());
		assertEquals(personalityTest.hashCode(),personality.hashCode());
		personalityTest.setNodeId(null);
		assertNotEquals(personality.hashCode(), personalityTest.hashCode());
		assertNotEquals(personalityTest.hashCode(), personality.hashCode());
		personality.setNodeId(null);
		personalityTest=new Personality(null, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals(personality.hashCode(), personalityTest.hashCode());
		assertNotEquals(personalityTest.hashCode(), personality.hashCode());
		personalityTest=new Personality(extroversion, null, conscientiousness, emotionalStability, openess);
		assertNotEquals(personality.hashCode(), personalityTest.hashCode());
		assertNotEquals(personalityTest.hashCode(), personality.hashCode());
		personalityTest=new Personality(extroversion,agreeableness, null, emotionalStability, openess);
		assertNotEquals(personality.hashCode(), personalityTest.hashCode());
		assertNotEquals(personalityTest.hashCode(), personality.hashCode());
		personalityTest=new Personality(extroversion, agreeableness, conscientiousness, null, openess);
		assertNotEquals(personality.hashCode(), personalityTest.hashCode());
		assertNotEquals(personalityTest.hashCode(), personality.hashCode());
		personalityTest=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, null);
		assertNotEquals(personality.hashCode(), personalityTest.hashCode());
		assertNotEquals(personalityTest.hashCode(), personality.hashCode());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Personality#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		Personality personalityTest=null;
		assertNotEquals(personalityTest,personality);
		assertNotEquals(personality,personalityTest);
		String notElection="";
		assertNotEquals(personality,notElection);
		personalityTest=DatabaseDataFixture.populatePersonality1();
		assertEquals(personalityTest,personalityTest);
		assertEquals(personalityTest,personality);
		personalityTest.setNodeId(54l);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);
		personality.setNodeId(null);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);
		personalityTest.setNodeId(null);
		assertEquals(personality, personalityTest);
		assertEquals(personalityTest, personality);
		
		personalityTest=new Personality(3.1F, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals(personality, personalityTest);
		personalityTest=new Personality(null, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);
		
		personalityTest=new Personality(extroversion, 2.2F, conscientiousness, emotionalStability, openess);
		assertNotEquals(personality, personalityTest);
		personalityTest=new Personality(extroversion, null, conscientiousness, emotionalStability, openess);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);
		
		personalityTest=new Personality(extroversion, agreeableness, 4.9F, emotionalStability, openess);
		assertNotEquals(personality, personalityTest);
		personalityTest=new Personality(extroversion, agreeableness, null, emotionalStability, openess);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);
		
		personalityTest=new Personality(extroversion, agreeableness, conscientiousness, 0.3F, openess);
		assertNotEquals(personality, personalityTest);
		personalityTest=new Personality(extroversion, agreeableness, conscientiousness, null, openess);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);
		
		personalityTest=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, 1.7F);
		assertNotEquals(personality, personalityTest);
		personalityTest=new Personality(extroversion, agreeableness, conscientiousness, emotionalStability, null);
		assertNotEquals(personality, personalityTest);
		assertNotEquals(personalityTest, personality);	
	}

}
