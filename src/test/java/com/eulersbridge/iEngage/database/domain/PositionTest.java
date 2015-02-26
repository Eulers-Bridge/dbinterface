/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PositionTest
{
	private Position position;
	private PositionDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		position=DatabaseDataFixture.populatePosition1();
		dets=position.toPositionDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#Position()}.
	 */
	@Test
	public final void testPosition()
	{
		Position positionTest=new Position();
		assertEquals("positionTest not of Position class",positionTest.getClass(),Position.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#fromPositionDetails(com.eulersbridge.iEngage.core.events.positions.PositionDetails)}.
	 */
	@Test
	public final void testFromPositionDetails()
	{
		Position positionTest=Position.fromPositionDetails(dets);
		assertEquals("electionTest not of Election class",positionTest.getClass(),Position.class);
		assertEquals("",dets.getNodeId(),positionTest.getNodeId());
		assertEquals("",dets.getDescription(),positionTest.getDescription());
		assertEquals("",dets.getName(),positionTest.getName());
		assertEquals("",dets.getElectionId(),positionTest.getElection().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#toPositionDetails()}.
	 */
	@Test
	public final void testToPositionDetails()
	{
		PositionDetails dets=position.toPositionDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),PositionDetails.class);
		assertEquals("",position.getNodeId(),dets.getNodeId());
		assertEquals("",position.getDescription(),dets.getDescription());
		assertEquals("",position.getName(),dets.getName());
		assertEquals("",position.getElection().getNodeId(),dets.getElectionId());
	}

	@Test
	public final void testToPositionDetailsUserNull()
	{
		Election election=null;
		position.setElection(election);
		PositionDetails dets=position.toPositionDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),PositionDetails.class);
		assertEquals("",position.getNodeId(),dets.getNodeId());
		assertEquals("",position.getDescription(),dets.getDescription());
		assertEquals("",position.getName(),dets.getName());
		assertEquals("",election,dets.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",position.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("",position.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=515l;
		assertNotEquals("",position.getNodeId(),id);
		position.setNodeId(id);
		assertEquals("",id,position.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals("",position.getName(),dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String name="Another name.";
		assertNotEquals("",position.getName(),name);
		position.setName(name);
		assertEquals("",name,position.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		assertEquals("",position.getDescription(),dets.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String description="Another description.";
		assertNotEquals("",position.getDescription(),description);
		position.setDescription(description);
		assertEquals("",description,position.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#getElection()}.
	 */
	@Test
	public final void testGetElection()
	{
		assertEquals("",position.getElection().getNodeId(),dets.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#setElection(com.eulersbridge.iEngage.database.domain.Election)}.
	 */
	@Test
	public final void testSetElection()
	{
		Election election=DatabaseDataFixture.populateElection2();
		assertNotEquals("",position.getElection(),election);
		position.setElection(election);
		assertEquals("",election,position.getElection());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Position positionTest=DatabaseDataFixture.populatePosition1();
		assertEquals(positionTest.hashCode(),positionTest.hashCode());
		assertEquals(positionTest.hashCode(),position.hashCode());
		positionTest.setNodeId(null);
		checkHashCode(position,positionTest);
		position.setNodeId(null);
		
		positionTest.setName(null);
		checkHashCode(position,positionTest);
		positionTest.setName(position.getName());
		
		positionTest.setDescription(null);
		checkHashCode(position,positionTest);
		positionTest.setDescription(position.getDescription());
		
		positionTest.setElection(null);;
		checkHashCode(position,positionTest);
		positionTest.setElection(position.getElection());
	}

	private void checkHashCode(Position test1,Position test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Position test1,Position test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Position#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		Position positionTest=null;
		assertNotEquals(positionTest,position);
		assertNotEquals(position,positionTest);
		String notElection="";
		assertNotEquals(position,notElection);
		positionTest=DatabaseDataFixture.populatePosition1();
		assertEquals(positionTest,positionTest);
		assertEquals(positionTest,position);
		
		positionTest.setNodeId(54l);
		checkNotEquals(position,positionTest);
		position.setNodeId(null);
		checkNotEquals(position,positionTest);
		positionTest.setNodeId(null);
		
		assertEquals(position, positionTest);
		assertEquals(positionTest, position);
		
		positionTest.setDescription("Some description");
		assertNotEquals(position, positionTest);
		positionTest.setDescription(null);
		checkNotEquals(positionTest, position);
		positionTest.setDescription(position.getDescription());
		
		positionTest.setName("title");
		assertNotEquals(position, positionTest);
		positionTest.setName(null);
		checkNotEquals(position, positionTest);
		positionTest.setName(position.getName());
		
		positionTest.setElection(null);
		checkNotEquals(position, positionTest);
		positionTest.setElection(position.getElection());
	}

}
