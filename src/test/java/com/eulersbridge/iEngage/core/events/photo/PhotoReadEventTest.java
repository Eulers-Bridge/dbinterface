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
public class PhotoReadEventTest
{
    Long nodeId;
    PhotoReadEvent photoReadEvent;
    Photo photo;
    PhotoDetails photoDetails;
    
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId=5432l;
		photo=DatabaseDataFixture.populatePhoto1();
		photoDetails=photo.toPhotoDetails();
		photoReadEvent=new PhotoReadEvent(nodeId, photoDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent#PhotoReadEvent(java.lang.Long)}.
	 */
	@Test
	public final void testPhotoReadEventLong()
	{
		photoReadEvent=new PhotoReadEvent(nodeId);
		assertNotNull(photoReadEvent);
		assertEquals(nodeId, photoReadEvent.getNodeId());
		assertNull(photoReadEvent.getPhotoDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent#PhotoReadEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}.
	 */
	@Test
	public final void testPhotoReadEventLongPhotoDetails()
	{
		assertNotNull(photoReadEvent);
		assertEquals(nodeId, photoReadEvent.getNodeId());
		assertEquals(photoDetails,photoReadEvent.getPhotoDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId, photoReadEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent#getPhotoDetails()}.
	 */
	@Test
	public final void testGetPhotoDetails()
	{
		assertEquals(photoDetails, photoReadEvent.getPhotoDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent#notFound(java.lang.Long)}.
	 */
	@Test
	public final void testNotFound()
	{
		photoReadEvent=PhotoReadEvent.notFound(nodeId);
		assertEquals(nodeId, photoReadEvent.getNodeId());
		assertFalse(photoReadEvent.isEntityFound());
	}

}
