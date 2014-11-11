/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class ReadPhotoEventTest
{
    Long nodeId;
    ReadPhotoEvent readPhotoEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId = 324l;
		readPhotoEvent=new ReadPhotoEvent(nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent#ReadPhotoEvent(java.lang.Long)}.
	 */
	@Test
	public final void testReadPhotoEvent()
	{
		assertNotNull(readPhotoEvent);
		assertEquals(nodeId, readPhotoEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId, readPhotoEvent.getNodeId());
	}

}
