/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PhotoDeletedEventTest
{
    Long nodeId;
    DeletedEvent photoDeletedEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId=5432l;
		photoDeletedEvent=new PhotoDeletedEvent(nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent#PhotoDeletedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testPhotoDeletedEvent()
	{
		assertNotNull(photoDeletedEvent);
		assertEquals(nodeId, photoDeletedEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent#deletionForbidden(java.lang.Long)}.
	 */
	@Test
	public final void testDeletionForbidden()
	{
		photoDeletedEvent=PhotoDeletedEvent.deletionForbidden(nodeId);
		assertFalse(photoDeletedEvent.isDeletionCompleted());
		assertTrue(photoDeletedEvent.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent#notFound(java.lang.Long)}.
	 */
	@Test
	public final void testNotFound()
	{
		photoDeletedEvent=PhotoDeletedEvent.notFound(nodeId);
		assertFalse(photoDeletedEvent.isDeletionCompleted());
		assertFalse(photoDeletedEvent.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent#isDeletionCompleted()}.
	 */
	@Test
	public final void testIsDeletionCompleted()
	{
		assertTrue(photoDeletedEvent.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId, photoDeletedEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		assertEquals(nodeId, photoDeletedEvent.getNodeId());
		nodeId++;
		assertNotEquals(nodeId, photoDeletedEvent.getNodeId());
		photoDeletedEvent.setNodeId(nodeId);
		assertEquals(nodeId, photoDeletedEvent.getNodeId());
	}

}
