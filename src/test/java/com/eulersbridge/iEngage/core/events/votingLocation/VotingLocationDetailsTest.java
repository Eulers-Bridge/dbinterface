/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.VotingLocation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationDetailsTest
{
	VotingLocation testVotingLocation;
	VotingLocationDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testVotingLocation=DatabaseDataFixture.populateVotingLocation1();
		dets=testVotingLocation.toVotingLocationDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.VotingLocationDetails#VotingLocationDetails(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)}.
	 */
	@Test
	public final void testVotingLocationDetails2()
	{
		dets=new VotingLocationDetails(testVotingLocation.getNodeId(), testVotingLocation.getName(), testVotingLocation.getInformation(), testVotingLocation.getOwner().getNodeId());
		assertNotNull("Not yet implemented",dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("Not yet implemented",testVotingLocation.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=324324l;
		dets.setNodeId(nodeId);
		assertEquals(nodeId,dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals("Not yet implemented",testVotingLocation.getName(),dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String name="Another name";
		dets.setName(name);
		assertEquals(name,dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#getInformation()}.
	 */
	@Test
	public final void testGetInformation()
	{
		assertEquals("Not yet implemented",testVotingLocation.getInformation(),dets.getInformation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#setInformation(java.lang.String)}.
	 */
	@Test
	public final void testSetInformation()
	{
		String information="Another information";
		dets.setInformation(information);
		assertEquals(information,dets.getInformation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#getOwnerId()}.
	 */
	@Test
	public final void testGetOwnerId()
	{
		assertEquals("Not yet implemented",testVotingLocation.getOwner().getNodeId(),dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails#setOwnerId(java.lang.Long)}.
	 */
	@Test
	public final void testSetOwnerId()
	{
		Long ownerId=321l;
		assertNotEquals("",dets.getOwnerId(),ownerId);
		dets.setOwnerId(ownerId);
		assertEquals("",ownerId,dets.getOwnerId());
	}

	private void checkHashCode(VotingLocationDetails test1,VotingLocationDetails test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(VotingLocationDetails test1,VotingLocationDetails test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		VotingLocationDetails votingLocationTest=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		assertEquals(votingLocationTest.hashCode(),votingLocationTest.hashCode());
		assertEquals(votingLocationTest.hashCode(),dets.hashCode());
		votingLocationTest.setNodeId(null);
		checkHashCode(dets,votingLocationTest);
		dets.setNodeId(null);
		
		votingLocationTest.setName(null);
		checkHashCode(dets,votingLocationTest);
		votingLocationTest.setName(dets.getName());
		
		votingLocationTest.setInformation(null);
		checkHashCode(dets,votingLocationTest);
		votingLocationTest.setInformation(dets.getInformation());
		
		votingLocationTest.setOwnerId(null);
		checkHashCode(dets,votingLocationTest);
		votingLocationTest.setOwnerId(dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		VotingLocationDetails votingLocationTest=null;
		assertNotEquals(votingLocationTest,dets);
		assertNotEquals(dets,votingLocationTest);
		String notElection="";
		assertNotEquals(dets,notElection);
		votingLocationTest=DatabaseDataFixture.populateVotingLocation1().toVotingLocationDetails();
		assertEquals(votingLocationTest,votingLocationTest);
		assertEquals(votingLocationTest,dets);
		
		votingLocationTest.setNodeId(54l);
		checkNotEquals(dets,votingLocationTest);
		dets.setNodeId(null);
		checkNotEquals(dets,votingLocationTest);
		votingLocationTest.setNodeId(null);
		
		assertEquals(dets, votingLocationTest);
		assertEquals(votingLocationTest, dets);
		
		votingLocationTest.setInformation("Some description");
		assertNotEquals(dets, votingLocationTest);
		votingLocationTest.setInformation(null);
		checkNotEquals(votingLocationTest, dets);
		votingLocationTest.setInformation(dets.getInformation());
		
		votingLocationTest.setName("title");
		assertNotEquals(dets, votingLocationTest);
		votingLocationTest.setName(null);
		checkNotEquals(dets, votingLocationTest);
		votingLocationTest.setName(dets.getName());
		
		dets.setOwnerId(null);
		checkNotEquals(dets, votingLocationTest);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",dets.toString());
	}

}
