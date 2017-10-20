/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class DeletePhotoDomainEventTest
{
    Long nodeId;
    DeletePhotoEvent deletePhotoEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId = 324l;
		deletePhotoEvent=new DeletePhotoEvent(nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent#DeletePhotoEvent(java.lang.Long)}.
	 */
	@Test
	public final void testDeletePhotoEvent()
	{
		assertNotNull(deletePhotoEvent);
		assertEquals(nodeId, deletePhotoEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId, deletePhotoEvent.getNodeId());
	}

}
