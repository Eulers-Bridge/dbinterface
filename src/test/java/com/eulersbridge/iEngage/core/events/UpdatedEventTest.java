package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class UpdatedEventTest
{
	Long nodeId;
	Details details;
	UpdatedEvent updatedEvent = null;

	@Before
	public void setUp() throws Exception
	{
		nodeId=1l;
		details=new Details(nodeId);
		updatedEvent = new UpdatedEvent(nodeId, details);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testUpdatedEventLongTest() throws Exception
	{
		updatedEvent= new UpdatedEvent(nodeId);
		assertNotNull("updatedEvent is null", updatedEvent);
		assertEquals(nodeId,updatedEvent.getNodeId());
	}
	
	@Test
	public void testUpdatedEventLongDetailsTest() throws Exception
	{
		assertNotNull("updatedEvent is null", updatedEvent);
		assertEquals(nodeId,updatedEvent.getNodeId());
		assertEquals(details,updatedEvent.getDetails());
	}
	
	@Test
	public void testGetNodeId() throws Exception
	{
		assertEquals(nodeId,updatedEvent.getNodeId());
	}

	@Test
	public void testGetDetails() throws Exception
	{
		assertEquals(details,updatedEvent.getDetails());
	}

	@Test
	public void testIsEntityFound() throws Exception
	{
		assertTrue(updatedEvent.isEntityFound());
	}

	@Test
	public void testNotFound()
	{
		updatedEvent = UpdatedEvent.notFound(nodeId);
		assertFalse(updatedEvent.isEntityFound());
		assertEquals(nodeId,updatedEvent.getNodeId());
	}
}