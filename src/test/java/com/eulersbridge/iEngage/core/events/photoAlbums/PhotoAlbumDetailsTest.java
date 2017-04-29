package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhotoAlbumDetailsTest
{
	PhotoAlbumDetails dets;
	PhotoAlbum photoAlbum;

	@Before
	public void setUp() throws Exception
	{
		photoAlbum=DatabaseDataFixture.populatePhotoAlbum1();
		dets=photoAlbum.toPhotoAlbumDetails();
	}

	@Test
	public final void testPhotoAlbumDetails()
	{
		dets=new PhotoAlbumDetails();
		assertNotNull("Not yet implemented",dets);
	}

	@Test
	public final void testPhotoAlbumDetails2()
	{
		String description="This description";
		Long modified=456l;
		String location="some location";
		String name="some name";
		String thumbNailUrl="some url";
		Long creatorId=54l;
		Long ownerId=43l;
		Long created=547l;
		Long nodeId=5l;
		dets=new PhotoAlbumDetails(nodeId, name, location, description, thumbNailUrl, creatorId, created, ownerId, modified);
		assertNotNull("Not yet implemented",dets);
		assertEquals(description,dets.getDescription());
		assertEquals(modified,dets.getModified());
		assertEquals(location,dets.getLocation());
		assertEquals(name,dets.getName());
		assertEquals(thumbNailUrl,dets.getThumbNailUrl());
		assertEquals(creatorId,dets.getCreatorId());
		assertEquals(ownerId,dets.getOwnerId());
		assertEquals(nodeId,dets.getNodeId());
		assertEquals(created,dets.getCreated());
	}

	@Test
	public final void testGetNodeId()
	{
		assertEquals(photoAlbum.getNodeId(),dets.getNodeId());
	}

	@Test
	public final void testSetNodeId()
	{
		Long nodeId=232323l;
		assertNotEquals(nodeId,dets.getNodeId());
		dets.setNodeId(nodeId);
		assertEquals(nodeId,dets.getNodeId());
	}

	@Test
	public final void testGetName()
	{
		assertEquals(photoAlbum.getName(),dets.getName());
	}

	@Test
	public final void testSetName()
	{
		String name="Some name";
		assertNotEquals(name,dets.getName());
		dets.setName(name);
		assertEquals(name,dets.getName());
	}

	@Test
	public final void testGetDescription()
	{
		assertEquals(photoAlbum.getDescription(),dets.getDescription());
	}

	@Test
	public final void testSetDescription()
	{
		String description="Some description";
		assertNotEquals(description,dets.getDescription());
		dets.setDescription(description);
		assertEquals(description,dets.getDescription());
	}

	@Test
	public final void testGetLocation()
	{
		assertEquals(photoAlbum.getLocation(),dets.getLocation());
	}

	@Test
	public final void testSetLocation()
	{
		String description="Some location";
		assertNotEquals(description,dets.getLocation());
		dets.setLocation(description);
		assertEquals(description,dets.getLocation());
	}

	@Test
	public final void testGetThumbNailUrl()
	{
		assertEquals(photoAlbum.getThumbNailUrl(),dets.getThumbNailUrl());
	}

	@Test
	public final void testSetThumbNailUrl()
	{
		String description="Some thumbnail";
		assertNotEquals(description,dets.getThumbNailUrl());
		dets.setThumbNailUrl(description);
		assertEquals(description,dets.getThumbNailUrl());
	}

	@Test
	public final void testGetCreated()
	{
		assertEquals(photoAlbum.getCreated(),dets.getCreated());
	}

	@Test
	public final void testSetCreated()
	{
		Long created=232323l;
		assertNotEquals(created,dets.getCreated());
		dets.setCreated(created);
		assertEquals(created,dets.getCreated());
	}

	@Test
	public final void testGetOwnerId()
	{
		assertEquals(photoAlbum.getOwner().getNodeId(),dets.getOwnerId());
	}

	@Test
	public final void testSetOwnerId()
	{
		Long ownerId=232323l;
		assertNotEquals(ownerId,dets.getOwnerId());
		dets.setOwnerId(ownerId);
		assertEquals(ownerId,dets.getOwnerId());
	}

	@Test
	public final void testGetModified()
	{
		assertEquals(photoAlbum.getModified(),dets.getModified());
	}

	@Test
	public final void testSetModified()
	{
		Long modified=232323l;
		assertNotEquals(modified,dets.getModified());
		dets.setModified(modified);
		assertEquals(modified,dets.getModified());
	}

	@Test
	public final void testHashCode()
	{
		PhotoAlbumDetails photoAlbumTest=DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
		PhotoAlbumDetails detsTest=dets;
		assertEquals(photoAlbumTest.hashCode(),photoAlbumTest.hashCode());
		assertEquals(photoAlbumTest.hashCode(),detsTest.hashCode());
		photoAlbumTest.setNodeId(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setNodeId(null);
		photoAlbumTest.setCreated(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setCreated(detsTest.getCreated());
		photoAlbumTest.setModified(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setModified(detsTest.getModified());
		photoAlbumTest.setOwnerId(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setOwnerId(detsTest.getOwnerId());
		photoAlbumTest.setName(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setName(detsTest.getName());
		photoAlbumTest.setDescription(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setDescription(detsTest.getDescription());

		photoAlbumTest.setLocation(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setLocation(detsTest.getLocation());

		photoAlbumTest.setThumbNailUrl(null);
		assertNotEquals(detsTest.hashCode(), photoAlbumTest.hashCode());
		assertNotEquals(photoAlbumTest.hashCode(), detsTest.hashCode());
		photoAlbumTest.setThumbNailUrl(detsTest.getThumbNailUrl());
}

	@Test
	public final void testEqualsObject()
	{
        PhotoAlbumDetails photoAlbumDetails = photoAlbum.toPhotoAlbumDetails();
        assertEquals("photoAlbumDetail does not match", photoAlbumDetails, dets);
        
        
		PhotoAlbumDetails photoAlbumTest=null;
		assertNotEquals(photoAlbumTest,photoAlbumDetails);
		assertNotEquals(photoAlbumDetails,photoAlbumTest);
		String notElection="";
		assertNotEquals(photoAlbumDetails,notElection);
		photoAlbumTest=DatabaseDataFixture.populatePhotoAlbum1().toPhotoAlbumDetails();
		assertEquals(photoAlbumTest,photoAlbumTest);
		assertEquals(photoAlbumTest,photoAlbumDetails);
		photoAlbumTest.setNodeId(54l);
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		assertNotEquals(photoAlbumTest, photoAlbumDetails);
		photoAlbumDetails.setNodeId(null);
		photoAlbumTest.setNodeId(null);
		photoAlbumTest.setCreated(photoAlbumDetails.getCreated());
		assertEquals(photoAlbumDetails, photoAlbumTest);
		assertEquals(photoAlbumTest, photoAlbumDetails);
		photoAlbumTest.setName("Something else");
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		photoAlbumTest.setName(null);
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		assertNotEquals(photoAlbumTest, photoAlbumDetails);
		photoAlbumTest.setName(photoAlbumDetails.getName());

		photoAlbumTest.setDescription("Something else");
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		photoAlbumTest.setDescription(null);
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		assertNotEquals(photoAlbumTest, photoAlbumDetails);
		photoAlbumTest.setDescription(photoAlbumDetails.getDescription());

		photoAlbumTest.setLocation("Some other location");
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		photoAlbumTest.setLocation(null);
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		assertNotEquals(photoAlbumTest, photoAlbumDetails);
		photoAlbumTest.setLocation(photoAlbumDetails.getLocation());

		photoAlbumTest.setThumbNailUrl("Some other thumbnail.");
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		photoAlbumTest.setThumbNailUrl(null);
		assertNotEquals(photoAlbumDetails, photoAlbumTest);
		assertNotEquals(photoAlbumTest, photoAlbumDetails);
		photoAlbumTest.setThumbNailUrl(photoAlbumDetails.getThumbNailUrl());

		photoAlbumTest.setCreated(54l);
		PhotoAlbumDetails dets2=photoAlbumDetails;
		assertNotEquals(dets2, photoAlbumTest);
		photoAlbumTest.setCreated(null);
		assertNotEquals(dets2, photoAlbumTest);
		assertNotEquals(photoAlbumTest, dets2);
		photoAlbumTest.setCreated(dets2.getCreated());
		
		photoAlbumTest.setModified(54l);
		assertNotEquals(dets2, photoAlbumTest);
		assertNotEquals(photoAlbumTest, dets2);
		photoAlbumTest.setModified(null);
		assertEquals(dets2, photoAlbumTest);
		photoAlbumTest.setModified(dets2.getModified());
		
		photoAlbumTest.setOwnerId(54l);
		assertNotEquals(dets2, photoAlbumTest);
		photoAlbumTest.setOwnerId(null);
		assertNotEquals(dets2, photoAlbumTest);
		assertNotEquals(photoAlbumTest, dets2);
		photoAlbumTest.setOwnerId(dets2.getOwnerId());
	}

	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",dets.toString());
	}

}
