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
public class PhotoDetailsTest
{
	Photo testPhoto;
	PhotoDetails dets;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testPhoto=DatabaseDataFixture.populatePhoto1();
		dets=testPhoto.toPhotoDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#PhotoDetails(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)}.
	 */
	@Test
	public final void testPhotoDetails2()
	{
		dets=new PhotoDetails(testPhoto.getNodeId(), testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.getOwner().getNodeId());
		assertNotNull("Not yet implemented",dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals("Not yet implemented",testPhoto.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=324324l;
		dets.setNodeId(nodeId);
		assertEquals(nodeId,dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getOwnerId()}.
	 */
	@Test
	public final void testGetOwnerId()
	{
		assertEquals("Not yet implemented",testPhoto.getOwner().getNodeId(),dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setOwnerId(java.lang.Long)}.
	 */
	@Test
	public final void testSetOwnerId()
	{
		Long ownerId=324324l;
		dets.setOwnerId(ownerId);
		assertEquals(ownerId,dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getOwnerId()}.
	 */
	@Test
	public final void testGetSequence()
	{
		assertEquals("Not yet implemented",testPhoto.getSequence(),dets.getSequence());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setOwnerId(java.lang.Long)}.
	 */
	@Test
	public final void testSetSequence()
	{
		Integer sequence=324324;
		dets.setSequence(sequence);
		assertEquals(sequence,dets.getSequence());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getUrl()}.
	 */
	@Test
	public final void testGetUrl()
	{
		assertEquals(testPhoto.getUrl(),dets.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setUrl(java.lang.String)}.
	 */
	@Test
	public final void testSetUrl()
	{
		String testUrl="testUrl";
		dets.setUrl(testUrl);
		assertEquals(testUrl,dets.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getThumbNailUrl()}.
	 */
	@Test
	public final void testGetThumbNailUrl()
	{
		assertEquals(testPhoto.getThumbNailUrl(),dets.getThumbNailUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setThumbNailUrl(java.lang.String)}.
	 */
	@Test
	public final void testSetThumbNailUrl()
	{
		String testThumbNailUrl="testThumbNailUrl";
		dets.setThumbNailUrl(testThumbNailUrl);
		assertEquals(testThumbNailUrl,dets.getThumbNailUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getTitle()}.
	 */
	@Test
	public final void testGetTitle()
	{
		assertEquals(testPhoto.getTitle(),dets.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle()
	{
		String title="test Title";
		dets.setTitle(title);;
		assertEquals(title,dets.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		assertEquals(testPhoto.getDescription(),dets.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String description="test Description";
		dets.setDescription(description);
		assertEquals(description,dets.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#getDate()}.
	 */
	@Test
	public final void testGetDate()
	{
		assertEquals(testPhoto.getDate(),dets.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetDate()
	{
		Long date=535443l;
		dets.setDate(date);
		assertEquals(date,dets.getDate());
	}

	private void checkHashCode(PhotoDetails test1,PhotoDetails test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(PhotoDetails test1,PhotoDetails test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		PhotoDetails photoTest=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		assertEquals(photoTest.hashCode(),photoTest.hashCode());
		assertEquals(photoTest.hashCode(),testPhoto.hashCode());
		photoTest.setNodeId(null);
		checkHashCode(dets,photoTest);
		testPhoto.setNodeId(null);
		
		photoTest.setDate(null);
		checkHashCode(dets,photoTest);
		photoTest.setDate(testPhoto.getDate());
		
		photoTest.setDescription(null);
		checkHashCode(dets,photoTest);
		photoTest.setDescription(testPhoto.getDescription());
		
		photoTest.setTitle(null);
		checkHashCode(dets,photoTest);
		photoTest.setTitle(testPhoto.getTitle());
		
		photoTest.setUrl(null);
		checkHashCode(dets,photoTest);
		photoTest.setUrl(testPhoto.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		PhotoDetails photoTest=null;
		assertNotEquals(photoTest,dets);
		assertNotEquals(dets,photoTest);
		String notElection="";
		assertNotEquals(dets,notElection);
		photoTest=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		assertEquals(photoTest,photoTest);
		assertEquals(photoTest,dets);
		
		photoTest.setNodeId(54l);
		checkNotEquals(dets,photoTest);
		dets.setNodeId(null);
		checkNotEquals(dets,photoTest);
		photoTest.setNodeId(null);
		photoTest.setDate(dets.getDate());
		assertEquals(dets, photoTest);
		assertEquals(photoTest, dets);
		
		photoTest.setDate(4321l);
		assertNotEquals(dets, photoTest);
		photoTest.setDate(null);
		checkNotEquals(dets, photoTest);
		photoTest.setDate(dets.getDate());

		photoTest.setOwnerId(4321l);
		assertNotEquals(dets, photoTest);
		photoTest.setOwnerId(null);
		checkNotEquals(dets, photoTest);
		photoTest.setOwnerId(dets.getOwnerId());

		photoTest.setDescription("Some description");
		assertNotEquals(dets, photoTest);
		photoTest.setDescription(null);
		checkNotEquals(photoTest, dets);
		photoTest.setDescription(dets.getDescription());
		
		photoTest.setTitle("title");
		assertNotEquals(dets, photoTest);
		photoTest.setTitle(null);
		checkNotEquals(dets, photoTest);
		photoTest.setTitle(dets.getTitle());
		
		photoTest.setUrl("url");
		assertNotEquals(dets, photoTest);
		photoTest.setUrl(null);
		checkNotEquals(dets, photoTest);
		photoTest.setUrl(dets.getUrl());

		photoTest.setThumbNailUrl("ThumbNailurl");
		assertNotEquals(dets, photoTest);
		photoTest.setThumbNailUrl(null);
		checkNotEquals(dets, photoTest);
		photoTest.setThumbNailUrl(dets.getThumbNailUrl());
}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",dets.toString());
	}

}
