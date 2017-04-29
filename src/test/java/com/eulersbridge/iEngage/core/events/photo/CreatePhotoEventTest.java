/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Photo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
		assertEquals(photoDetails, createPhotoEvent.getDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent#getEventDetails()}
	 * .
	 */
	@Test
	public final void testGetPhotoDetails()
	{
		assertEquals(photoDetails,createPhotoEvent.getDetails());
	}

	/**
	 * Test method for
	 * {@link com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent#setEventDetails(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}
	 * .
	 */
	@Test
	public final void testSetPhotoDetails()
	{
		assertEquals(photoDetails,createPhotoEvent.getDetails());
		photoDetails=DatabaseDataFixture.populatePhoto2().toPhotoDetails();
		assertNotEquals(photoDetails,createPhotoEvent.getDetails());
		createPhotoEvent.setDetails(photoDetails);
		assertEquals(photoDetails,createPhotoEvent.getDetails());
	}

}
