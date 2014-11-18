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
public class PhotoAlbumDeletedEventTest
{
	PhotoAlbumDeletedEvent evt1;
	PhotoAlbumDeletedEvent evt2;
	Long nodeId;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId=1l;
		evt1=new PhotoAlbumDeletedEvent(nodeId);
		evt2=new PhotoAlbumDeletedEvent(nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent#PhotoAlbumDeletedEvent(java.lang.Long)}.
	 */
	@Test
	public final void testPhotoAlbumDeletedEvent()
	{
		assertNotNull("Not yet implemented",evt1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent#deletionForbidden(java.lang.Long)}.
	 */
	@Test
	public final void testDeletionForbidden()
	{
		evt1=PhotoAlbumDeletedEvent.deletionForbidden(nodeId);
		assertNotNull("Not yet implemented",evt1);
		assertEquals(evt1.getNodeId(),nodeId);
		assertFalse(evt1.isDeletionCompleted());
		assertTrue(evt1.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent#notFound(java.lang.Long)}.
	 */
	@Test
	public final void testNotFound()
	{
		evt1=PhotoAlbumDeletedEvent.notFound(nodeId);
		assertNotNull("Not yet implemented",evt1);
		assertEquals(evt1.getNodeId(),nodeId);
		assertFalse(evt1.isDeletionCompleted());
		assertFalse(evt1.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent#isDeletionCompleted()}.
	 */
	@Test
	public final void testIsDeletionCompleted()
	{
		evt1=PhotoAlbumDeletedEvent.deletionForbidden(nodeId);
		assertNotNull("Not yet implemented",evt1);
		assertFalse(evt1.isDeletionCompleted());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId,evt1.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent#setEventId(java.lang.Long)}.
	 */
	@Test
	public final void testSetEventId()
	{
		assertEquals(nodeId,evt1.getNodeId());
		nodeId=5435345l;
		evt1.setNodeId(nodeId);
		assertEquals(nodeId,evt1.getNodeId());
	}

}
