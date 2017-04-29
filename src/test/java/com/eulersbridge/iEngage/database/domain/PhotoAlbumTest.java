/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PhotoAlbumTest
{
	PhotoAlbum photoAlbum;
	PhotoAlbum photoAlbum2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		photoAlbum=DatabaseDataFixture.populatePhotoAlbum1();
		photoAlbum2=DatabaseDataFixture.populatePhotoAlbum1();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#PhotoAlbum()}.
	 */
	@Test
	public final void testPhotoAlbum()
	{
		assertNotNull("Not yet implemented",photoAlbum);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(photoAlbum.getNodeId(),photoAlbum2.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=543l;
		assertNotEquals(nodeId,photoAlbum.getNodeId());
		photoAlbum.setNodeId(nodeId);
		assertEquals(nodeId,photoAlbum.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getName()}.
	 */
	@Test
	public final void testGetName()
	{
		assertEquals(photoAlbum.getName(),photoAlbum2.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName()
	{
		String name="new Name";
		assertNotEquals(name,photoAlbum.getName());
		photoAlbum.setName(name);
		assertEquals(name,photoAlbum.getName());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		assertEquals(photoAlbum.getDescription(),photoAlbum2.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String description="new Description";
		assertNotEquals(description,photoAlbum.getDescription());
		photoAlbum.setDescription(description);
		assertEquals(description,photoAlbum.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getLocation()}.
	 */
	@Test
	public final void testGetLocation()
	{
		assertEquals(photoAlbum.getLocation(),photoAlbum2.getLocation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setLocation(java.lang.String)}.
	 */
	@Test
	public final void testSetLocation()
	{
		String location="new Location";
		assertNotEquals(location,photoAlbum.getLocation());
		photoAlbum.setLocation(location);
		assertEquals(location,photoAlbum.getLocation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getThumbNailUrl()}.
	 */
	@Test
	public final void testGetThumbNailUrl()
	{
		assertEquals(photoAlbum.getThumbNailUrl(),photoAlbum2.getThumbNailUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setThumbNailUrl(java.lang.String)}.
	 */
	@Test
	public final void testSetThumbNailUrl()
	{
		String thumbNailUrl="new ThumbNailUrl";
		assertNotEquals(thumbNailUrl,photoAlbum.getThumbNailUrl());
		photoAlbum.setThumbNailUrl(thumbNailUrl);
		assertEquals(thumbNailUrl,photoAlbum.getThumbNailUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getCreated()}.
	 */
	@Test
	public final void testGetCreated()
	{
		photoAlbum.setCreated(photoAlbum2.getCreated());
		assertEquals(photoAlbum.getCreated(),photoAlbum2.getCreated());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setCreated(java.lang.Long)}.
	 */
	@Test
	public final void testSetCreated()
	{
		Long created=543l;
		assertNotEquals(created,photoAlbum.getCreated());
		photoAlbum.setCreated(created);
		assertEquals(created,photoAlbum.getCreated());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getOwner()}.
	 */
	@Test
	public final void testGetOwner()
	{
		assertEquals(photoAlbum.getOwner(),photoAlbum2.getOwner());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setOwner(com.eulersbridge.iEngage.database.domain.Owner)}.
	 */
	@Test
	public final void testSetOwner()
	{
		Owner owner=new Owner(543l);
		assertNotEquals(owner,photoAlbum.getOwner());
		photoAlbum.setOwner(owner);
		assertEquals(owner,photoAlbum.getOwner());
		Owner owner2=new Owner(543l);
		assertEquals(owner2,photoAlbum.getOwner());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#getModified()}.
	 */
	@Test
	public final void testGetModified()
	{
		assertEquals(photoAlbum.getModified(),photoAlbum2.getModified());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#setModified(java.lang.Long)}.
	 */
	@Test
	public final void testSetModified()
	{
		Long modified=543l;
		assertNotEquals(modified,photoAlbum.getModified());
		photoAlbum.setModified(modified);
		assertEquals(modified,photoAlbum.getModified());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#fromPhotoAlbumDetails(com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails)}.
	 */
	@Test
	public final void testFromPhotoAlbumDetails()
	{
		PhotoAlbumDetails dets=photoAlbum.toPhotoAlbumDetails();
		photoAlbum2=PhotoAlbum.fromPhotoAlbumDetails(dets);
		assertEquals(photoAlbum.getNodeId(), photoAlbum2.getNodeId());
		assertEquals(photoAlbum.getName(), photoAlbum2.getName());
		assertEquals(photoAlbum.getDescription(), photoAlbum2.getDescription());
		assertEquals(photoAlbum.getCreated(), photoAlbum2.getCreated());
		assertEquals(photoAlbum.getModified(), photoAlbum2.getModified());
		assertEquals(photoAlbum.getOwner(), photoAlbum2.getOwner());
		assertEquals(photoAlbum.getCreator(), photoAlbum2.getCreator());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#toPhotoAlbumDetails()}.
	 */
	@Test
	public final void testToPhotoAlbumDetails()
	{
		PhotoAlbumDetails dets=photoAlbum.toPhotoAlbumDetails();
		assertEquals(photoAlbum.getNodeId(), dets.getNodeId());
		assertEquals(photoAlbum.getName(), dets.getName());
		assertEquals(photoAlbum.getDescription(), dets.getDescription());
		assertEquals(photoAlbum.getCreated(), dets.getCreated());
		assertEquals(photoAlbum.getModified(), dets.getModified());
		assertEquals(photoAlbum.getOwner().getNodeId(), dets.getOwnerId());
		assertEquals(photoAlbum.getCreator().getNodeId(), dets.getCreatorId());
	}
	@Test
	public final void testToPhotoAlbumDetailsCreatorNull()
	{
		photoAlbum.setCreator(null);
		PhotoAlbumDetails dets=photoAlbum.toPhotoAlbumDetails();
		assertEquals(photoAlbum.getNodeId(), dets.getNodeId());
		assertEquals(photoAlbum.getName(), dets.getName());
		assertEquals(photoAlbum.getDescription(), dets.getDescription());
		assertEquals(photoAlbum.getCreated(), dets.getCreated());
		assertEquals(photoAlbum.getModified(), dets.getModified());
		assertEquals(photoAlbum.getOwner().getNodeId(), dets.getOwnerId());
		assertNull(dets.getCreatorId());
	}
	@Test
	public final void testToPhotoAlbumDetailsOwnerNull()
	{
		photoAlbum.setOwner(null);
		PhotoAlbumDetails dets=photoAlbum.toPhotoAlbumDetails();
		assertEquals(photoAlbum.getNodeId(), dets.getNodeId());
		assertEquals(photoAlbum.getName(), dets.getName());
		assertEquals(photoAlbum.getDescription(), dets.getDescription());
		assertEquals(photoAlbum.getCreated(), dets.getCreated());
		assertEquals(photoAlbum.getModified(), dets.getModified());
		assertNull(dets.getOwnerId());
		assertEquals(photoAlbum.getCreator().getNodeId(), dets.getCreatorId());
	}

	private void checkHashCode(PhotoAlbum test1,PhotoAlbum test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(PhotoAlbum test1,PhotoAlbum test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		assertEquals(photoAlbum.hashCode(),photoAlbum.hashCode());
		assertEquals(photoAlbum.hashCode(),photoAlbum2.hashCode());
		photoAlbum.setNodeId(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setNodeId(null);
		photoAlbum2.setName(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setName(photoAlbum.getName());
		photoAlbum2.setDescription(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setDescription(photoAlbum.getDescription());
		
		photoAlbum2.setLocation(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setLocation(photoAlbum.getLocation());

		photoAlbum2.setThumbNailUrl(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setThumbNailUrl(photoAlbum.getThumbNailUrl());
		
		photoAlbum2.setCreated(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setCreated(photoAlbum.getCreated());
		photoAlbum2.setModified(4532l);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setModified(photoAlbum.getModified());
		photoAlbum2.setOwner(null);
		checkHashCode(photoAlbum,photoAlbum2);
		photoAlbum2.setOwner(photoAlbum.getOwner());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		photoAlbum2=null;
		assertNotEquals(photoAlbum2,photoAlbum);
		assertNotEquals(photoAlbum,photoAlbum2);
		String notElection="";
		assertNotEquals(photoAlbum,notElection);
		photoAlbum2=DatabaseDataFixture.populatePhotoAlbum1();
		assertEquals(photoAlbum2,photoAlbum2);
		assertEquals(photoAlbum2,photoAlbum);
		photoAlbum2.setNodeId(54l);
		checkNotEquals(photoAlbum,photoAlbum2);
		photoAlbum.setNodeId(null);
		checkNotEquals(photoAlbum,photoAlbum2);
		photoAlbum2.setNodeId(null);
		photoAlbum2.setCreated(photoAlbum.getCreated());
		assertEquals(photoAlbum, photoAlbum2);
		assertEquals(photoAlbum2, photoAlbum);
		photoAlbum2.setCreated(4321l);
		assertNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setCreated(null);
		checkNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setCreated(photoAlbum.getCreated());

		photoAlbum2.setDescription("Some description");
		assertNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setDescription(null);
		checkNotEquals(photoAlbum2, photoAlbum);
		photoAlbum2.setDescription(photoAlbum.getDescription());
		
		photoAlbum2.setLocation("Some location");
		assertNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setLocation(null);
		checkNotEquals(photoAlbum2, photoAlbum);
		photoAlbum2.setLocation(photoAlbum.getLocation());
		
		photoAlbum2.setThumbNailUrl("Some thumbNailUrl");
		assertNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setThumbNailUrl(null);
		checkNotEquals(photoAlbum2, photoAlbum);
		photoAlbum2.setThumbNailUrl(photoAlbum.getThumbNailUrl());
		
		photoAlbum2.setModified(54l);
		checkNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setModified(null);
		assertEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setModified(photoAlbum.getModified());
		
		photoAlbum.setName("some name");
		assertNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setName(null);
		checkNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setName(photoAlbum.getName());
		
		photoAlbum2.setOwner(new Owner(54l));
		assertNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setOwner(null);
		checkNotEquals(photoAlbum, photoAlbum2);
		photoAlbum2.setOwner(photoAlbum.getOwner());
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.PhotoAlbum#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",photoAlbum.toString());
	}

}
