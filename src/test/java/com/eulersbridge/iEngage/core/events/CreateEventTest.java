package com.eulersbridge.iEngage.core.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yikai Gong
 */

public class CreateEventTest
{
	CreateEvent createEvent;
	Details dets;
	Long nodeId;

	@Before
	public void setUp() throws Exception
	{
		nodeId=42l;
		dets = new Details(nodeId);
		createEvent = new CreateEvent(dets);
	}

	@Test
	public void testCreateEvent() throws Exception
	{
		assertNotNull("createEvent is null", createEvent);
		assertEquals(dets,createEvent.getDetails());
	}

	@Test
	public void testGetDetails() throws Exception
	{
		assertEquals(dets,createEvent.getDetails());
	}
}