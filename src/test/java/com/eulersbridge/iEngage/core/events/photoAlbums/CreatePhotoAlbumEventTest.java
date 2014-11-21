package com.eulersbridge.iEngage.core.events.photoAlbums;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

public class CreatePhotoAlbumEventTest
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

	@Test
	public final void testGetPhotoDetails()
	{
	}

	@Test
	public final void testSetPhotoDetails()
	{
	}

}
