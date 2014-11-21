/**
 * 
 */
package com.eulersbridge.iEngage.core.events;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class DeleteEventTest
{
	DeleteEvent evt;
	Long nodeId;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId=12l;
		evt=new DeleteEvent(nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.DeleteEvent#DeleteEvent(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteEvent()
	{
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.DeleteEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(evt.getNodeId(),nodeId);
	}

}
