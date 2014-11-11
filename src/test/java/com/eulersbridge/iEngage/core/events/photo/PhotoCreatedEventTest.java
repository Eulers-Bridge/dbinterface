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
public class PhotoCreatedEventTest
{
	PhotoDetails photoDetails;
	Photo photo;
	PhotoCreatedEvent photoCreatedEvent;
	Long nodeId;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		photo=DatabaseDataFixture.populatePhoto1();
		nodeId=photo.getNodeId();
		photoDetails=photo.toPhotoDetails();
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#PhotoCreatedEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}
	 * .
	 */
	@Test
	public final void testPhotoCreatedEventLongPhotoDetails()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId,photoDetails);
		assertNotNull("Not yet implemented",photoCreatedEvent);
		assertEquals(photoDetails, photoCreatedEvent.getPhotoDetails());
		assertEquals(nodeId,photoCreatedEvent.getNodeId());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#PhotoCreatedEvent(java.lang.Long)}
	 * .
	 */
	@Test
	public final void testPhotoCreatedEventLong()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId);
		assertNotNull("Not yet implemented",photoCreatedEvent);
		assertNull(photoCreatedEvent.getPhotoDetails());
		assertEquals(nodeId,photoCreatedEvent.getNodeId());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#getPhotoDetails()}
	 * .
	 */
	@Test
	public final void testGetPhotoDetails()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId,photoDetails);
		assertEquals(photoDetails, photoCreatedEvent.getPhotoDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#setPhotoDetails(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}
	 * .
	 */
	@Test
	public final void testSetPhotoDetails()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId);
		assertNotNull("Not yet implemented",photoCreatedEvent);
		assertNull(photoCreatedEvent.getPhotoDetails());
		photoCreatedEvent.setPhotoDetails(photoDetails);
		assertEquals(photoDetails, photoCreatedEvent.getPhotoDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#getNodeId()}
	 * .
	 */
	@Test
	public final void testGetNodeId()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId,photoDetails);
		assertEquals(nodeId, photoCreatedEvent.getNodeId());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#setNodeId(java.lang.Long)}
	 * .
	 */
	@Test
	public final void testSetNodeId()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId);
		assertNotNull("Not yet implemented",photoCreatedEvent);
		assertEquals(nodeId,photoCreatedEvent.getNodeId());
		Long newNodeId=454343l;
		photoCreatedEvent.setNodeId(newNodeId);
		assertEquals(newNodeId,photoCreatedEvent.getNodeId());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#isOwnerFound()}
	 * .
	 */
	@Test
	public final void testIsOwnerFound()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId,photoDetails);
		assertTrue(photoCreatedEvent.isOwnerFound());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#setOwnerFound(boolean)}
	 * .
	 */
	@Test
	public final void testSetOwnerFound()
	{
		photoCreatedEvent=new PhotoCreatedEvent(nodeId,photoDetails);
		assertTrue(photoCreatedEvent.isOwnerFound());
		photoCreatedEvent.setOwnerFound(false);
		assertFalse(photoCreatedEvent.isOwnerFound());
		photoCreatedEvent.setOwnerFound(true);
		assertTrue(photoCreatedEvent.isOwnerFound());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent#ownerNotFound(java.lang.Long)}
	 * .
	 */
	@Test
	public final void testOwnerNotFound()
	{
		Long ownerId=31l;
		photoCreatedEvent=PhotoCreatedEvent.ownerNotFound(ownerId);
		assertNotNull("Not yet implemented",photoCreatedEvent);
		assertNull(photoCreatedEvent.getPhotoDetails());
		assertEquals(ownerId,photoCreatedEvent.getNodeId());
		assertFalse(photoCreatedEvent.isOwnerFound());
	}

}