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
public class CreatePhotoEventTest
{
	Photo photo;
    PhotoDetails photoDetails;
    CreatePhotoEvent createPhotoEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		photo = DatabaseDataFixture.populatePhoto1();
		photoDetails=photo.toPhotoDetails();
		createPhotoEvent=new CreatePhotoEvent(photoDetails);
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent#CreatePhotoEvent(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}
	 * .
	 */
	@Test
	public final void testCreatePhotoEventPhotoDetails()
	{
		assertNotNull(createPhotoEvent);
		assertEquals(photoDetails, createPhotoEvent.getPhotoDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent#getEventDetails()}
	 * .
	 */
	@Test
	public final void testGetPhotoDetails()
	{
		assertEquals(photoDetails,createPhotoEvent.getPhotoDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent#setEventDetails(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}
	 * .
	 */
	@Test
	public final void testSetPhotoDetails()
	{
		assertEquals(photoDetails,createPhotoEvent.getPhotoDetails());
		photoDetails=DatabaseDataFixture.populatePhoto2().toPhotoDetails();
		assertNotEquals(photoDetails,createPhotoEvent.getPhotoDetails());
		createPhotoEvent.setPhotoDetails(photoDetails);
		assertEquals(photoDetails,createPhotoEvent.getPhotoDetails());
	}

}
