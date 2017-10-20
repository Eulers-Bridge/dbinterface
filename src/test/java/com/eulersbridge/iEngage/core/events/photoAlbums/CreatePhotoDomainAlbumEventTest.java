package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreatePhotoDomainAlbumEventTest
{
	PhotoAlbum photoAlbum;
    PhotoAlbumDetails photoAlbumDetails;
    CreatePhotoAlbumEvent createPhotoAlbumEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		photoAlbum = DatabaseDataFixture.populatePhotoAlbum1();
		photoAlbumDetails=photoAlbum.toPhotoAlbumDetails();
		createPhotoAlbumEvent=new CreatePhotoAlbumEvent(photoAlbumDetails);
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.CreatePhotoAlbumEvent#CreatePhotoAlbumEvent(com.eulersbridge.iEngage.core.events.photoAlbum.PhotoAlbumDetails)}
	 * .
	 */
	@Test
	public final void testCreatePhotoAlbumEvent()
	{
		assertNotNull(createPhotoAlbumEvent);
		assertEquals(photoAlbumDetails, createPhotoAlbumEvent.getDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photoAlbum.CreatePhotoAlbumEvent#getDetails()}
	 * .
	 */
	@Test
	public final void testGetPhotoAlbumDetails()
	{
		assertEquals(photoAlbumDetails,createPhotoAlbumEvent.getDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photoAlbum.CreatePhotoAlbumEvent#setDetails(com.eulersbridge.iEngage.core.events.photoAlbum.PhotoAlbumDetails)}
	 * .
	 */
	@Test
	public final void testSetPhotoAlbumDetails()
	{
		assertEquals(photoAlbumDetails,createPhotoAlbumEvent.getDetails());
		photoAlbumDetails=DatabaseDataFixture.populatePhotoAlbum2().toPhotoAlbumDetails();
		assertNotEquals(photoAlbumDetails,createPhotoAlbumEvent.getDetails());
		createPhotoAlbumEvent.setDetails(photoAlbumDetails);
		assertEquals(photoAlbumDetails,createPhotoAlbumEvent.getDetails());
	}
}
