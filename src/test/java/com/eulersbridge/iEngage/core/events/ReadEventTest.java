package com.eulersbridge.iEngage.core.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ReadEventTest
{
	ReadEvent readEvent;
	Long nodeId;
	Details dets;

	@Before
	public void setUp() throws Exception
	{
		nodeId = 4234l;
		dets = new Details(nodeId);
		readEvent = new ReadEvent(nodeId, dets);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testReadEventLong() throws Exception
	{
		readEvent = new ReadEvent(nodeId);
		assertNotNull("readEvent is null");
	}

	@Test
	public void testReadEventLongDetails() throws Exception
	{
		assertNotNull("readEvent is null");
	}

	@Test
	public void testGetNodeId() throws Exception
	{
		assertEquals("entityFound is not true", nodeId,readEvent.getNodeId());
	}
	
	@Test
	public void testGeDetails() throws Exception
	{
		assertEquals("entityFound is not true", dets,readEvent.getDetails());
	}
	
	@Test
	public void testIsEntityFound() throws Exception
	{
		assertTrue("entityFound is not true", readEvent.isEntityFound());
	}
}