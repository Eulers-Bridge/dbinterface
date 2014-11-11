/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PhotoUpdatedEventTest
{
    Photo photo;
    PhotoDetails photoDetails;
    Long nodeId;
    PhotoUpdatedEvent photoUpdatedEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		photo=DatabaseDataFixture.populatePhoto1();
		photoDetails=photo.toPhotoDetails();
		nodeId=12356l;
		photoUpdatedEvent=new PhotoUpdatedEvent(nodeId, photoDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent#PhotoUpdatedEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}.
	 */
	@Test
	public final void testPhotoUpdatedEventLongPhotoDetails()
	{
		assertNotNull(photoUpdatedEvent);
		assertEquals(photoDetails, photoUpdatedEvent.getPhotoDetails());
		assertEquals(nodeId, photoUpdatedEvent.getNodeId());
		assertTrue(photoUpdatedEvent.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent#PhotoUpdatedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testPhotoUpdatedEventLong()
	{
		photoUpdatedEvent=new PhotoUpdatedEvent(nodeId);
		assertNotNull(photoUpdatedEvent);
		assertNull(photoUpdatedEvent.getPhotoDetails());
		assertEquals(nodeId, photoUpdatedEvent.getNodeId());
		assertTrue(photoUpdatedEvent.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId, photoUpdatedEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent#getPhotoDetails()}.
	 */
	@Test
	public final void testGetPhotoDetails()
	{
		assertEquals(photoDetails, photoUpdatedEvent.getPhotoDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent#notFound(java.lang.Long)}.
	 */
	@Test
	public final void testNotFound()
	{
		photoUpdatedEvent=PhotoUpdatedEvent.notFound(nodeId);
		assertNotNull(photoUpdatedEvent);
		assertNull(photoUpdatedEvent.getPhotoDetails());
		assertEquals(nodeId, photoUpdatedEvent.getNodeId());
		assertFalse(photoUpdatedEvent.isEntityFound());
	}

}
