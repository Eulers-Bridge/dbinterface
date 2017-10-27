/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationTest
{
	private VotingLocation votingLocation;
	private VotingLocationDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		votingLocation=DatabaseDataFixture.populateVotingLocation1();
		dets=votingLocation.toVotingLocationDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#VotingLocation()}.
	 */
	@Test
	public final void testVotingLocation()
	{
		VotingLocation candidateTest=new VotingLocation();
		assertEquals("candidateTest not of VotingLocation class",candidateTest.getClass(),VotingLocation.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#VotingLocation()}.
	 */
	@Test
	public final void testVotingLocationLongStringStringUserPosition()
	{
		VotingLocation votingLocationTest=new VotingLocation(votingLocation.getNodeId(),votingLocation.getName(),votingLocation.getInformation(),votingLocation.getOwner());
		assertEquals("electionTest not of Election class",votingLocationTest.getClass(),VotingLocation.class);
		assertEquals("",votingLocation.getNodeId(),votingLocationTest.getNodeId());
		assertEquals("",votingLocation.getInformation(),votingLocationTest.getInformation());
		assertEquals("",votingLocation.getName(),votingLocationTest.getName());
		assertEquals("",votingLocation.getOwner(),votingLocationTest.getOwner());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#fromVotingLocationDetails(com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails)}.
	 */
	@Test
	public final void testFromVotingLocationDetails()
	{
		VotingLocation locationTest=VotingLocation.fromVotingLocationDetails(dets);
		assertEquals("locationTest not of VotingLocation class",locationTest.getClass(),VotingLocation.class);
		assertEquals("",dets.getNodeId(),locationTest.getNodeId());
		assertEquals("",dets.getInformation(),locationTest.getInformation());
		assertEquals("",dets.getName(),locationTest.getName());
		assertEquals("",dets.getOwnerId(),locationTest.getOwner().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#toVotingLocationDetails()}.
	 */
	@Test
	public final void testToVotingLocationDetails()
	{
		VotingLocationDetails dets=votingLocation.toVotingLocationDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),VotingLocationDetails.class);
		assertEquals("",votingLocation.getNodeId(),dets.getNodeId());
		assertEquals("",votingLocation.getInformation(),dets.getInformation());
		assertEquals("",votingLocation.getName(),dets.getName());
		assertEquals("",votingLocation.getOwner().getNodeId(),dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("",votingLocation.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=234234l;
		assertNotEquals("",votingLocation.getNodeId(),nodeId);
		votingLocation.setNodeId(nodeId);
		assertEquals("",nodeId,votingLocation.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals("",votingLocation.getName(),dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String name="Whatever";
		assertNotEquals("",votingLocation.getName(),name);
		votingLocation.setName(name);
		assertEquals("",name,votingLocation.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#getInformation()}.
	 */
	@Test
	public final void testGetInformation()
	{
		assertEquals("",votingLocation.getInformation(),dets.getInformation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#setInformation(java.lang.String)}.
	 */
	@Test
	public final void testSetInformation()
	{
		String information="Whatever";
		assertNotEquals("",votingLocation.getInformation(),information);
		votingLocation.setInformation(information);
		assertEquals("",information,votingLocation.getInformation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#getOwner()}.
	 */
	@Test
	public final void testGetOwner()
	{
		assertEquals("",votingLocation.getOwner().getNodeId(),dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.VotingLocation#(com.eulersbridge.iEngage.database.domain)}.
	 */
	@Test
	public final void testSetOwner()
	{
		Node owner=new Node(321l);
		assertNotEquals("",votingLocation.getOwner(),owner);
		votingLocation.setOwner(owner);
		assertEquals("",owner,votingLocation.getOwner());
	}

	private void checkHashCode(VotingLocation test1,VotingLocation test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(VotingLocation test1,VotingLocation test2)
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
		VotingLocation votingLocationTest=DatabaseDataFixture.populateVotingLocation1();
		assertEquals(votingLocationTest.hashCode(),votingLocationTest.hashCode());
		assertEquals(votingLocationTest.hashCode(),votingLocation.hashCode());
		votingLocationTest.setNodeId(null);
		checkHashCode(votingLocation,votingLocationTest);
		votingLocation.setNodeId(null);
		
		votingLocationTest.setName(null);
		checkHashCode(votingLocation,votingLocationTest);
		votingLocationTest.setName(votingLocation.getName());
		
		votingLocationTest.setInformation(null);
		checkHashCode(votingLocation,votingLocationTest);
		votingLocationTest.setInformation(votingLocation.getInformation());
		
		votingLocationTest.setOwner(null);;
		checkHashCode(votingLocation,votingLocationTest);
		votingLocationTest.setOwner(votingLocation.getOwner());
		}



	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",votingLocation.toString());
	}

}
