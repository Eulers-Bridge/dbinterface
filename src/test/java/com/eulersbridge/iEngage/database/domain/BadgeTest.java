/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.badge.BadgeDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class BadgeTest
{
	private Badge badge;
	private BadgeDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		badge=DatabaseDataFixture.populateBadge1();
		dets=badge.toBadgeDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#Badge()}.
	 */
	@Test
	public final void testBadge()
	{
		Badge candidateTest=new Badge();
		assertEquals("candidateTest not of Badge class",candidateTest.getClass(),Badge.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#fromBadgeDetails(com.eulersbridge.iEngage.core.events.badge.BadgeDetails)}.
	 */
	@Test
	public final void testFromBadgeDetails()
	{
		Badge badgeTest=Badge.fromBadgeDetails(dets);
		assertEquals("electionTest not of Election class",badgeTest.getClass(),Badge.class);
		assertEquals("",dets.getNodeId(),badgeTest.getNodeId());
		assertEquals("",dets.getName(),badgeTest.getName());
		assertEquals("",dets.getDescription(),badgeTest.getDescription());
		assertEquals("",dets.getXpValue(),badgeTest.getXpValue());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#toBadgeDetails()}.
	 */
	@Test
	public final void testToBadgeDetails()
	{
		BadgeDetails dets=badge.toBadgeDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),BadgeDetails.class);
		assertEquals("",badge.getNodeId(),dets.getNodeId());
		assertEquals("",badge.getName(),dets.getName());
		assertEquals("",badge.getDescription(),dets.getDescription());
		assertEquals("",badge.getXpValue(),dets.getXpValue());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",badge.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("",badge.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=515l;
		assertNotEquals("",badge.getNodeId(),id);
		badge.setNodeId(id);
		assertEquals("",id,badge.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals("",badge.getName(),dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String information="Whatever";
		assertNotEquals("",badge.getName(),information);
		badge.setName(information);
		assertEquals("",information,badge.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#getXpValue()}.
	 */
	@Test
	public final void testGetXpValue()
	{
		assertEquals("",badge.getXpValue(),dets.getXpValue());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Badge#setXpValue(java.lang.Long)}.
	 */
	@Test
	public final void testSetXpValue()
	{
		Long xpValue=515l;
		assertNotEquals("",badge.getXpValue(),xpValue);
		badge.setXpValue(xpValue);
		assertEquals("",xpValue,badge.getXpValue());
	}

	private void checkHashCode(Badge test1,Badge test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Badge test1,Badge test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Badge badgeTest=DatabaseDataFixture.populateBadge1();
		assertEquals(badgeTest.hashCode(),badgeTest.hashCode());
		assertEquals(badgeTest.hashCode(),badge.hashCode());
		badgeTest.setNodeId(null);
		checkHashCode(badge,badgeTest);
		badge.setNodeId(null);
		
		badgeTest.setName(null);
		checkHashCode(badge,badgeTest);
		badgeTest.setName(badge.getName());
		
		badgeTest.setDescription(null);
		checkHashCode(badge,badgeTest);
		badgeTest.setDescription(badge.getDescription());
		
		badgeTest.setXpValue(null);
		checkHashCode(badge,badgeTest);
		badgeTest.setXpValue(badge.getXpValue());
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		Badge badgeTest=null;
		assertNotEquals(badgeTest,badge);
		assertNotEquals(badge,badgeTest);
		String notElection="";
		assertNotEquals(badge,notElection);
		badgeTest=DatabaseDataFixture.populateBadge1();
		assertEquals(badgeTest,badgeTest);
		assertEquals(badgeTest,badge);
		
		badgeTest.setNodeId(54l);
		checkNotEquals(badge,badgeTest);
		badge.setNodeId(null);
		checkNotEquals(badge,badgeTest);
		badgeTest.setNodeId(null);
		
		assertEquals(badge, badgeTest);
		assertEquals(badgeTest, badge);
		
		badgeTest.setName("Some name");
		assertNotEquals(badge, badgeTest);
		badgeTest.setName(null);
		checkNotEquals(badgeTest, badge);
		badgeTest.setName(badge.getName());
		
		badgeTest.setDescription("Some description");
		assertNotEquals(badge, badgeTest);
		badgeTest.setDescription(null);
		checkNotEquals(badgeTest, badge);
		badgeTest.setDescription(badge.getDescription());
		
		badgeTest.setXpValue(123l);
		assertNotEquals(badge, badgeTest);
		badgeTest.setXpValue(null);
		checkNotEquals(badge, badgeTest);
		badgeTest.setXpValue(badge.getXpValue());
	}

}
