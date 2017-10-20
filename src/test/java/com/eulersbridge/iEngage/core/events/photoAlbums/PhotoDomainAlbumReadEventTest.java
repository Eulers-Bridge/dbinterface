/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PhotoDomainAlbumReadEventTest
{
	PhotoAlbumReadEvent evt;
	Long nodeId;
	PhotoAlbumDetails photoAlbumDetails;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId=2222l;
		photoAlbumDetails=DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
		evt=new PhotoAlbumReadEvent(nodeId, photoAlbumDetails);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumReadEvent#PhotoAlbumReadEvent(java.lang.Long)}.
	 */
	@Test
	public final void testPhotoAlbumReadEventLong()
	{
		evt=new PhotoAlbumReadEvent(nodeId);
		assertNotNull("Not yet implemented",evt);
		assertEquals(nodeId,evt.getNodeId());
		assertNull(evt.getDetails());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumReadEvent#PhotoAlbumReadEvent(java.lang.Long, com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails)}.
	 */
	@Test
	public final void testPhotoAlbumReadEventLongPhotoAlbumDetails()
	{
		assertNotNull("Not yet implemented",evt);
		assertEquals(nodeId,evt.getNodeId());
		assertEquals(photoAlbumDetails,evt.getDetails());
	}
}
