/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PersonalityDetailsTest {

	private static final float delta = 0;
	private float emotionalStability=3.4F;
	private float agreeableness=4.3F;
	private float openess=4.1F;
	private float conscientiousness=3.8F;
	private float extroversion=2.5F;
	private float extroversion2=4.5F;
	PersonalityDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		dets=new PersonalityDetails(null, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#hashCode()}.
	 */
	@Test
	public final void testHashCode() 
	{
		PersonalityDetails dets2=new PersonalityDetails(null, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Identical dets hashcode not equal",dets.hashCode(),dets2.hashCode());
		
		dets2=new PersonalityDetails(null, extroversion2, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals("Non-identical dets hashcode equal.",dets.hashCode(),dets2.hashCode());
		
		dets2=new PersonalityDetails(2L, extroversion2, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals("Non-identical dets equal.",dets.hashCode(),dets2.hashCode());
				
		dets=new PersonalityDetails(1L, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals("Non-identical dets equal.",dets.hashCode(),dets2.hashCode());

		dets=new PersonalityDetails(2L, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Identical dets not equal",dets.hashCode(),dets2.hashCode());
		
		assertNotEquals("Object equal to null",dets.hashCode(), null);
		
		assertNotEquals("Object equal to different object",dets.hashCode(),new Object().hashCode());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#PersonalityDetails()}.
	 */
	@Test
	public final void testPersonalityDetails() 
	{
		assertNotNull("Not constructed",new PersonalityDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#PersonalityDetails(java.lang.Long, float, float, float, float, float)}.
	 */
	@Test
	public final void testPersonalityDetailsLongFloatFloatFloatFloatFloat() 
	{
		assertNotNull("Not constructed.",new PersonalityDetails(null, extroversion, agreeableness, conscientiousness, emotionalStability, openess));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#getPersonalityId()}.
	 */
	@Test
	public final void testGetPersonalityId() 
	{
		assertEquals("Null ids equal",  dets.getPersonalityId(), null);
		dets=new PersonalityDetails(1L, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("NonNull ids equal",  dets.getPersonalityId(), new Long(1));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#setPersonalityId(java.lang.Long)}.
	 */
	@Test
	public final void testSetPersonalityId() 
	{
		dets.setPersonalityId(1L);
		assertEquals("Ids not equal",  dets.getPersonalityId(), new Long(1));
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#getExtroversion()}.
	 */
	@Test
	public final void testGetExtroversion() 
	{
		assertEquals("extroversions not equal.",  dets.getExtroversion(), extroversion,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#setExtroversion(float)}.
	 */
	@Test
	public final void testSetExtroversion() 
	{
		dets.setExtroversion(extroversion2);
		assertEquals("extroversions not equal.",dets.getExtroversion(),extroversion2,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#getAgreeableness()}.
	 */
	@Test
	public final void testGetAgreeableness() 
	{
		assertEquals("agreeablesness not equal.",  dets.getAgreeableness(), agreeableness,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#setAgreeableness(float)}.
	 */
	@Test
	public final void testSetAgreeableness() 
	{
		float agree2=0.2F;
		dets.setAgreeableness(agree2);
		assertEquals("agreeablesness not equal.",dets.getAgreeableness(),agree2,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#getConscientiousness()}.
	 */
	@Test
	public final void testGetConscientiousness() 
	{
		assertEquals("conscientiousness not equal.",  dets.getConscientiousness(), conscientiousness,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#setConscientiousness(float)}.
	 */
	@Test
	public final void testSetConscientiousness() {
		float consc2=0.2F;
		dets.setConscientiousness(consc2);
		assertEquals("conscientiousness not equal.",dets.getConscientiousness(),consc2,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#getEmotionalStability()}.
	 */
	@Test
	public final void testGetEmotionalStability() 
	{
		assertEquals("emotional stability not equal.",  dets.getEmotionalStability(), emotionalStability,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#setEmotionalStability(float)}.
	 */
	@Test
	public final void testSetEmotionalStability() 
	{
		float emo2=0.2F;
		dets.setEmotionalStability(emo2);
		assertEquals("emotional stability not equal.",dets.getEmotionalStability(),emo2,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#getOpeness()}.
	 */
	@Test
	public final void testGetOpeness() {
		assertEquals("openess not equal.",  dets.getOpeness(), openess,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#setOpeness(float)}.
	 */
	@Test
	public final void testSetOpeness() 
	{
		float open2=0.2F;
		dets.setOpeness(open2);
		assertEquals("openess not equal.",dets.getOpeness(),open2,delta);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#toString()}.
	 */
	@Test
	public final void testToString() 
	{
		assertNotNull("No toString() returned.",dets.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.users.PersonalityDetails#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() 
	{
		PersonalityDetails dets2=new PersonalityDetails(null, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Identical dets not equal",dets,dets2);
		
		dets2=new PersonalityDetails(null, extroversion2, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals("Non-identical dets equal.",dets,dets2);
		
		dets2=new PersonalityDetails(2L, extroversion2, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals("Non-identical dets equal.",dets,dets2);
		
		dets=new PersonalityDetails(1L, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertNotEquals("Non-identical dets equal.",dets,dets2);

		dets=new PersonalityDetails(2L, extroversion, agreeableness, conscientiousness, emotionalStability, openess);
		assertEquals("Identical dets not equal",dets,dets2);
		
		assertNotEquals("Object equal to null",dets, null);
		
		assertNotEquals("Object equal to different object",dets,new Object());
	}

}
