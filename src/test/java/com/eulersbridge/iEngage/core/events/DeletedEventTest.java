package com.eulersbridge.iEngage.core.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class DeletedEventTest
{
	DeletedEvent deletedEvent;
	Long nodeId;

	@Before
	public void setUp() throws Exception
	{
		nodeId = 232l;
		deletedEvent = new DeletedEvent(nodeId);
	}

	@Test
	public void testDeletedEvent() throws Exception
	{
		assertNotNull("deletedEvent is null", deletedEvent);
		assertEquals(deletedEvent.getNodeId(), nodeId);
	}

	@Test
	public void testIsEntityFound() throws Exception
	{
		assertTrue("entityFound is not true", deletedEvent.isEntityFound());
	}

	@Test
	public void testGetNodeId() throws Exception
	{
		assertEquals(nodeId, deletedEvent.getNodeId());
	}

	@Test
	public void testSetNodeId() throws Exception
	{
		assertEquals(nodeId, deletedEvent.getNodeId());
		nodeId=242l;
		assertNotEquals(nodeId, deletedEvent.getNodeId());
		deletedEvent.setNodeId(nodeId);
		assertEquals(nodeId, deletedEvent.getNodeId());
	}

	@Test
	public void testDeletionForbidden() throws Exception
	{
		DeletedEvent deletedEvent1 = DeletedEvent.deletionForbidden(nodeId);
		assertNotNull("deletedEvent is null", deletedEvent1);
		assertFalse("deletionCompleted is not false",
				deletedEvent1.isDeletionCompleted());
	}

	@Test
	public void testNotFound() throws Exception
	{
		DeletedEvent deletedEvent1 = DeletedEvent.notFound(nodeId);
		assertNotNull("deletedEvent is null", deletedEvent1);
		assertFalse("entityFound is not false", deletedEvent1.isEntityFound());

	}

}