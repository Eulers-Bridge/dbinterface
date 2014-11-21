package com.eulersbridge.iEngage.core.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class RequestReadEventTest
{
	RequestReadEvent requestReadEvent = null;
	Long nodeId;

	@Before
	public void setUp() throws Exception
	{
		nodeId=4321l;
		requestReadEvent = new RequestReadEvent(nodeId);
	}

	@Test
	public void testRequestReadEvent() throws Exception
	{
		assertNotNull("requestReadEvent is null", requestReadEvent);
		assertEquals(nodeId,requestReadEvent.getNodeId());
	}
	
	@Test
	public void testGetNodeId() throws Exception
	{
		assertEquals(nodeId,requestReadEvent.getNodeId());
	}
}