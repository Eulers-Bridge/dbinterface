/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photoAlbums;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PhotoAlbumCreatedEventTest
{
	PhotoAlbumDetails dets;
	PhotoAlbumCreatedEvent evt;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		dets=DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
		evt=new PhotoAlbumCreatedEvent(dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent#PhotoAlbumCreatedEvent(com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails)}.
	 */
	@Test
	public final void testPhotoAlbumCreatedEventPhotoAlbumDetails()
	{
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent#getEventDetails()}.
	 */
	@Test
	public final void testGetDetails()
	{
		assertEquals(evt.getDetails(),dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent#setEventDetails(com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails)}.
	 */
	@Test
	public final void testSetDetails()
	{
		assertEquals(evt.getDetails(),dets);
		PhotoAlbumDetails dets2=DatabaseDataFixture.populatePhotoAlbum2().toPhotoAlbumDetails();
		evt.setDetails(dets2);
		assertNotEquals(evt.getDetails(),dets);
		assertEquals(evt.getDetails(),dets2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent#isOwnerFound()}.
	 */
	@Test
	public final void testIsOwnerFound()
	{
		assertTrue(evt.isOwnerFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent#setOwnerFound(boolean)}.
	 */
	@Test
	public final void testSetOwnerFound()
	{
		assertTrue(evt.isOwnerFound());
		evt.setOwnerFound(false);
		assertFalse(evt.isOwnerFound());
		evt.setOwnerFound(true);
		assertTrue(evt.isOwnerFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent#ownerNotFound(java.lang.Long)}.
	 */
	@Test
	public final void testInstitutionNotFound()
	{
		Long institutionId=22l;
		evt=PhotoAlbumCreatedEvent.ownerNotFound(institutionId);
		assertFalse(evt.isOwnerFound());
		assertNull(evt.getDetails());
	}

}
