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
public class UpdatePhotoEventTest
{
    Photo photo;
    PhotoDetails photoDetails;
    Long nodeId;
    UpdatePhotoEvent updatePhotoEvent;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		photo=DatabaseDataFixture.populatePhoto1();
		photoDetails=photo.toPhotoDetails();
		nodeId=43211l;
		updatePhotoEvent=new UpdatePhotoEvent(nodeId, photoDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent#UpdatePhotoEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}.
	 */
	@Test
	public final void testUpdatePhotoEvent()
	{
		assertNotNull(updatePhotoEvent);
		assertEquals(nodeId,updatePhotoEvent.getNodeId());
		assertEquals(photoDetails,updatePhotoEvent.getPhotoDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId,updatePhotoEvent.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent#getPhotoDetails()}.
	 */
	@Test
	public final void testGetPhotoDetails()
	{
		assertEquals(photoDetails,updatePhotoEvent.getPhotoDetails());
	}

}
