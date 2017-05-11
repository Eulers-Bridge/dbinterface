/**
 * 
 */
package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PositionDetailsTest
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

	@Test
	public final void testPositionDetails()
	{
		dets=new PositionDetails();
		assertNotNull("Not yet implemented",dets);
	}

	@Test
	public final void testPositionDetails2()
	{
		String description="This description";
		String name="some name";
		Long electionId=54l;
		Long nodeId=5l;
		dets=new PositionDetails(nodeId, name, description, electionId);
		assertNotNull("Not yet implemented",dets);
		assertEquals(description,dets.getDescription());
		assertEquals(name,dets.getName());
		assertEquals(electionId,dets.getElectionId());
		assertEquals(nodeId,dets.getNodeId());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		PositionDetails positionTest=DatabaseDataFixture.populatePosition1().toPositionDetails();
		PositionDetails detsTest=dets;
		assertEquals(positionTest.hashCode(),positionTest.hashCode());
		assertEquals(positionTest.hashCode(),detsTest.hashCode());
		positionTest.setNodeId(null);
		assertNotEquals(detsTest.hashCode(), positionTest.hashCode());
		assertNotEquals(positionTest.hashCode(), detsTest.hashCode());
		positionTest.setNodeId(null);
		positionTest.setElectionId(null);
		assertNotEquals(detsTest.hashCode(), positionTest.hashCode());
		assertNotEquals(positionTest.hashCode(), detsTest.hashCode());
		positionTest.setElectionId(detsTest.getElectionId());
		positionTest.setName(null);
		assertNotEquals(detsTest.hashCode(), positionTest.hashCode());
		assertNotEquals(positionTest.hashCode(), detsTest.hashCode());
		positionTest.setName(detsTest.getName());
		positionTest.setDescription(null);
		assertNotEquals(detsTest.hashCode(), positionTest.hashCode());
		assertNotEquals(positionTest.hashCode(), detsTest.hashCode());
		positionTest.setDescription(detsTest.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
        PositionDetails positionDetails = position.toPositionDetails();
        assertEquals("positionDetail does not match", positionDetails, dets);
        
        
        PositionDetails positionTest=null;
		assertNotEquals(positionTest,positionDetails);
		assertNotEquals(positionDetails,positionTest);
		String notElection="";
		assertNotEquals(positionDetails,notElection);
		positionTest=DatabaseDataFixture.populatePosition1().toPositionDetails();
		assertEquals(positionTest,positionTest);
		assertEquals(positionTest,positionDetails);
		positionTest.setNodeId(54l);
		assertNotEquals(positionDetails, positionTest);
		assertNotEquals(positionTest, positionDetails);
		positionDetails.setNodeId(null);
		positionTest.setNodeId(null);
		positionTest.setName("Something else");
		assertNotEquals(positionDetails, positionTest);
		positionTest.setName(null);
		assertNotEquals(positionDetails, positionTest);
		assertNotEquals(positionTest, positionDetails);
		positionTest.setName(positionDetails.getName());

		positionTest.setDescription("Something else");
		assertNotEquals(positionDetails, positionTest);
		positionTest.setDescription(null);
		assertNotEquals(positionDetails, positionTest);
		assertNotEquals(positionTest, positionDetails);
		positionTest.setDescription(positionDetails.getDescription());
		
		positionTest.setElectionId(54l);
		assertNotEquals(positionDetails, positionTest);
		positionTest.setElectionId(null);
		assertNotEquals(positionDetails, positionTest);
		assertNotEquals(positionTest, positionDetails);
		positionTest.setElectionId(positionDetails.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",dets.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(dets.getNodeId(),position.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=232323l;
		assertNotEquals(nodeId,dets.getNodeId());
		dets.setNodeId(nodeId);
		assertEquals(nodeId,dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals(position.getName(),dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String name="Some name";
		assertNotEquals(name,dets.getName());
		dets.setName(name);
		assertEquals(name,dets.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		assertEquals(position.getDescription(),dets.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String description="Some description";
		assertNotEquals(description,dets.getDescription());
		dets.setDescription(description);
		assertEquals(description,dets.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#getElectionId()}.
	 */
	@Test
	public final void testGetElectionId()
	{
		assertEquals(dets.getElectionId(),position.getElection$().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.positions.PositionDetails#setElectionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetElectionId()
	{
		Long electionId=232323l;
		assertNotEquals(electionId,dets.getElectionId());
		dets.setElectionId(electionId);
		assertEquals(electionId,dets.getElectionId());
	}

}
