/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photoAlbums;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class PhotoAlbumsReadEventTest
{
	PhotoAlbumsReadEvent evt;
	private Long instId;
	private Collection<PhotoAlbumDetails> photoAlbums;
	ArrayList<PhotoAlbumDetails> dets;
	private long totalElements;
	private int pages;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		instId=34l;
		photoAlbums=new ArrayList<PhotoAlbumDetails>();
		totalElements=3l;
		pages=1;
		dets=new ArrayList<PhotoAlbumDetails>();
		evt=new PhotoAlbumsReadEvent(instId, dets, totalElements, pages);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#PhotoAlbumsReadEvent()}.
	 */
	@Test
	public final void testPhotoAlbumsReadEvent()
	{
		evt=new PhotoAlbumsReadEvent();
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#PhotoAlbumsReadEvent(java.lang.Long, java.util.Collection)}.
	 */
	@Test
	public final void testPhotoAlbumsReadEventLongCollectionOfPhotoAlbumDetails()
	{
		evt=new PhotoAlbumsReadEvent(instId, photoAlbums);
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#PhotoAlbumsReadEvent(java.lang.Long, java.util.ArrayList, long, int)}.
	 */
	@Test
	public final void testPhotoAlbumsReadEventLongArrayListOfPhotoAlbumDetailsLongInt()
	{
		assertNotNull("Not yet implemented",evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#getInstId()}.
	 */
	@Test
	public final void testGetInstId()
	{
		assertEquals(evt.getInstId(),instId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setInstId(java.lang.Long)}.
	 */
	@Test
	public final void testSetInstId()
	{
		Long instId2=instId+1;
		assertEquals(evt.getInstId(),instId);
		assertNotEquals(evt.getInstId(),instId2);
		evt.setInstId(instId2);
		assertEquals(evt.getInstId(),instId2);
		assertNotEquals(evt.getInstId(),instId);
		evt.setInstId(instId);
		assertEquals(evt.getInstId(),instId);
		assertNotEquals(evt.getInstId(),instId2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#isNewsFeedFound()}.
	 */
	@Test
	public final void testIsNewsFeedFound()
	{
		assertTrue(evt.isNewsFeedFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setNewsFeedFound(boolean)}.
	 */
	@Test
	public final void testSetNewsFeedFound()
	{
		assertTrue(evt.isNewsFeedFound());
		evt.setNewsFeedFound(false);
		assertFalse(evt.isNewsFeedFound());
		evt.setNewsFeedFound(true);
		assertTrue(evt.isNewsFeedFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#isInstitutionFound()}.
	 */
	@Test
	public final void testIsInstitutionFound()
	{
		assertTrue(evt.isInstitutionFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setInstitutionFound(boolean)}.
	 */
	@Test
	public final void testSetInstitutionFound()
	{
		assertTrue(evt.isInstitutionFound());
		evt.setInstitutionFound(false);
		assertFalse(evt.isInstitutionFound());
		evt.setInstitutionFound(true);
		assertTrue(evt.isInstitutionFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#isEventsFound()}.
	 */
	@Test
	public final void testIsEventsFound()
	{
		assertTrue(evt.isEventsFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setEventsFound(boolean)}.
	 */
	@Test
	public final void testSetEventsFound()
	{
		assertTrue(evt.isEventsFound());
		evt.setEventsFound(false);
		assertFalse(evt.isEventsFound());
		evt.setEventsFound(true);
		assertTrue(evt.isEventsFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#getTotalEvents()}.
	 */
	@Test
	public final void testGetTotalEvents()
	{
		assertEquals(evt.getTotalEvents().intValue(),totalElements);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setTotalEvents(java.lang.Long)}.
	 */
	@Test
	public final void testSetTotalEvents()
	{
		long events2=totalElements+1;
		assertEquals(evt.getTotalEvents().longValue(),totalElements);
		assertNotEquals(evt.getTotalEvents().longValue(),events2);
		evt.setTotalEvents(events2);
		assertEquals(evt.getTotalEvents().longValue(),events2);
		assertNotEquals(evt.getTotalEvents().longValue(),totalElements);
		evt.setTotalEvents(totalElements);
		assertEquals(evt.getTotalEvents().longValue(),totalElements);
		assertNotEquals(evt.getTotalEvents().longValue(),events2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#getTotalPages()}.
	 */
	@Test
	public final void testGetTotalPages()
	{
		assertEquals(evt.getTotalPages().intValue(),pages);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setTotalPages(java.lang.Integer)}.
	 */
	@Test
	public final void testSetTotalPages()
	{
		int pages2=pages+1;
		assertEquals(evt.getTotalPages().intValue(),pages);
		assertNotEquals(evt.getTotalPages().intValue(),pages2);
		evt.setTotalPages(pages2);
		assertEquals(evt.getTotalPages().intValue(),pages2);
		assertNotEquals(evt.getTotalPages().intValue(),pages);
		evt.setTotalPages(pages);
		assertEquals(evt.getTotalPages().intValue(),pages);
		assertNotEquals(evt.getTotalPages().intValue(),pages2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#getPhotoAlbums()}.
	 */
	@Test
	public final void testGetPhotoAlbums()
	{
		assertEquals(evt.getPhotoAlbums(),dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#setPhotoAlbums(java.util.Collection)}.
	 */
	@Test
	public final void testSetPhotoAlbums()
	{
		ArrayList<PhotoAlbumDetails> dets2=null;
		assertEquals(evt.getPhotoAlbums(),dets);
		assertNotEquals(evt.getPhotoAlbums(),dets2);
		evt.setPhotoAlbums(dets2);
		assertNull(evt.getPhotoAlbums());
		assertNotEquals(evt.getPhotoAlbums(),dets);
		assertEquals(evt.getPhotoAlbums(),dets2);
		evt.setPhotoAlbums(dets);
		assertNotNull(evt.getPhotoAlbums());
		assertNotEquals(evt.getPhotoAlbums(),dets2);
		assertEquals(evt.getPhotoAlbums(),dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#newsFeedNotFound()}.
	 */
	@Test
	public final void testNewsFeedNotFound()
	{
		evt=PhotoAlbumsReadEvent.newsFeedNotFound();
		assertNull(evt.getDetails());
		assertFalse(evt.isNewsFeedFound());
		assertFalse(evt.isEventsFound());
		assertFalse(evt.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent#institutionNotFound()}.
	 */
	@Test
	public final void testInstitutionNotFound()
	{
		evt=PhotoAlbumsReadEvent.institutionNotFound();
		assertNull(evt.getDetails());
		assertFalse(evt.isNewsFeedFound());
		assertFalse(evt.isEventsFound());
		assertFalse(evt.isEntityFound());
		assertFalse(evt.isInstitutionFound());
	}

}
