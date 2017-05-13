/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PhotoTest
{
	Photo testPhoto;
	Photo testPhoto2;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testPhoto=DatabaseDataFixture.populatePhoto1();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#Photo()}.
	 */
	@Test
	public final void testPhoto1()
	{
		testPhoto=new Photo();
		assertNotNull("Not yet implemented",testPhoto);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)}.
	 */
	@Test
	public final void testPhoto2()
	{
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(), testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertNotNull("Not yet implemented",testPhoto2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		testPhoto2.setNodeId(testPhoto.getNodeId());
		assertEquals(testPhoto.getNodeId(),testPhoto2.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=23l;
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertNull(testPhoto2.getNodeId());
		testPhoto2.setNodeId(nodeId);
		assertEquals(nodeId,testPhoto2.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getNodeId()}.
	 */
	@Test
	public final void testGetOwner()
	{
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		testPhoto2.setNodeId(testPhoto.getNodeId());
		testPhoto2.setOwner(testPhoto.getOwner());
		assertEquals(testPhoto.getOwner(),testPhoto2.getOwner());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetOwner()
	{
		Node owner=new Node(23l);
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertNull(testPhoto2.getOwner());
		testPhoto2.setOwner(owner);;
		assertEquals(owner,testPhoto2.getOwner());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getUrl()}.
	 */
	@Test
	public final void testGetUrl()
	{
		String url=testPhoto.getUrl();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(url,testPhoto2.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#(java.lang.String)}.
	 */
	@Test
	public final void testSetURL()
	{
		String url=testPhoto.getUrl();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(url,testPhoto2.getUrl());
		url="testUrl";
		testPhoto2.setUrl(url);
		assertEquals(url,testPhoto2.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getUrl()}.
	 */
	@Test
	public final void testGetThumbNailUrl()
	{
		String thumbNailurl=testPhoto.getThumbNailUrl();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(thumbNailurl,testPhoto2.getThumbNailUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#(java.lang.String)}.
	 */
	@Test
	public final void testSetThumbNailURL()
	{
		String thumbNailurl=testPhoto.getUrl();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(thumbNailurl,testPhoto2.getUrl());
		thumbNailurl="testUrl";
		testPhoto2.setUrl(thumbNailurl);
		assertEquals(thumbNailurl,testPhoto2.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getTitle()}.
	 */
	@Test
	public final void testGetTitle()
	{
		String title=testPhoto.getTitle();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(title,testPhoto2.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle()
	{
		String title=testPhoto.getTitle();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(title,testPhoto2.getTitle());
		title="testTitle";
		testPhoto2.setTitle(title);
		assertEquals(title,testPhoto2.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		String description=testPhoto.getDescription();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(description,testPhoto2.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String description=testPhoto.getDescription();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(description,testPhoto2.getDescription());
		description="testDescription";
		testPhoto2.setDescription(description);
		assertEquals(description,testPhoto2.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getDate()}.
	 */
	@Test
	public final void testGetDate()
	{
		Long date=testPhoto.getDate();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(date,testPhoto2.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetDate()
	{
		Long date=testPhoto.getDate();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(date,testPhoto2.getDate());
		date=4949494l;
		testPhoto2.setDate(date);
		assertEquals(date,testPhoto2.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getDate()}.
	 */
	@Test
	public final void testGetSequence()
	{
		Integer sequence=testPhoto.getSequence();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(sequence,testPhoto2.getSequence());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetSequence()
	{
		Integer sequence=testPhoto.getSequence();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getThumbNailUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate(),testPhoto.getSequence(),testPhoto.isInappropriateContent());
		assertEquals(sequence,testPhoto2.getSequence());
		sequence=4949494;
		testPhoto2.setSequence(sequence);
		assertEquals(sequence,testPhoto2.getSequence());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",testPhoto.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#toPhotoDetails()}.
	 */
	@Test
	public final void testToPhotoDetails()
	{
		PhotoDetails dets=testPhoto.toPhotoDetails();
		assertEquals(dets.getDate(),testPhoto.getDate());
		assertEquals(dets.getDescription(),testPhoto.getDescription());
		assertEquals(dets.getNodeId(),testPhoto.getNodeId());
		assertEquals(dets.getTitle(),testPhoto.getTitle());
		assertEquals(dets.getUrl(),testPhoto.getUrl());
		assertEquals(dets.getThumbNailUrl(),testPhoto.getThumbNailUrl());
		assertEquals(dets.getSequence(),testPhoto.getSequence());
		assertEquals(dets.getOwnerId(),testPhoto.getOwner().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#toPhotoDetails()}.
	 */
	@Test
	public final void testToPhotoDetailsOwnerNull()
	{
		testPhoto.setOwner(null);
		PhotoDetails dets=testPhoto.toPhotoDetails();
		assertEquals(dets.getDate(),testPhoto.getDate());
		assertEquals(dets.getDescription(),testPhoto.getDescription());
		assertEquals(dets.getNodeId(),testPhoto.getNodeId());
		assertEquals(dets.getTitle(),testPhoto.getTitle());
		assertEquals(dets.getUrl(),testPhoto.getUrl());
		assertEquals(dets.getThumbNailUrl(),testPhoto.getThumbNailUrl());
		assertEquals(dets.getSequence(),testPhoto.getSequence());
		assertNull(dets.getOwnerId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#fromPhotoDetails(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}.
	 */
	@Test
	public final void testFromPhotoDetails()
	{
		PhotoDetails dets=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		testPhoto2=Photo.fromPhotoDetails(dets);
		assertEquals(dets.getDate(),testPhoto2.getDate());
		assertEquals(dets.getDescription(),testPhoto2.getDescription());
		assertEquals(dets.getNodeId(),testPhoto2.getNodeId());
		assertEquals(dets.getTitle(),testPhoto2.getTitle());
		assertEquals(dets.getUrl(),testPhoto2.getUrl());
	}

	private void checkHashCode(Photo test1,Photo test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Photo test1,Photo test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Photo photoTest=DatabaseDataFixture.populatePhoto1();
		assertEquals(photoTest.hashCode(),photoTest.hashCode());
		assertEquals(photoTest.hashCode(),testPhoto.hashCode());
		photoTest.setNodeId(null);
		checkHashCode(testPhoto,photoTest);
		testPhoto.setNodeId(null);
		
		photoTest.setDate(null);
		checkHashCode(testPhoto,photoTest);
		photoTest.setDate(testPhoto.getDate());
		
		photoTest.setDescription(null);
		checkHashCode(testPhoto,photoTest);
		photoTest.setDescription(testPhoto.getDescription());
		
		photoTest.setTitle(null);
		checkHashCode(testPhoto,photoTest);
		photoTest.setTitle(testPhoto.getTitle());
		
		photoTest.setUrl(null);;
		checkHashCode(testPhoto,photoTest);
		photoTest.setUrl(testPhoto.getUrl());
		
		photoTest.setThumbNailUrl(null);
		checkHashCode(testPhoto,photoTest);
		photoTest.setThumbNailUrl(testPhoto.getThumbNailUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		Photo photoTest=null;
		assertNotEquals(photoTest,testPhoto);
		assertNotEquals(testPhoto,photoTest);
		String notElection="";
		assertNotEquals(testPhoto,notElection);
		photoTest=DatabaseDataFixture.populatePhoto1();
		assertEquals(photoTest,photoTest);
		assertEquals(photoTest,testPhoto);
		
		photoTest.setNodeId(54l);
		checkNotEquals(testPhoto,photoTest);
		testPhoto.setNodeId(null);
		checkNotEquals(testPhoto,photoTest);
		photoTest.setNodeId(null);
		// We are setting everything up to be equal here.  We need to equalize the dates as these 
		// are generated when the object is created.
		photoTest.setDate(testPhoto.getDate());
		
		assertEquals(testPhoto, photoTest);
		assertEquals(photoTest, testPhoto);
		
		photoTest.setDate(4321l);
		assertNotEquals(testPhoto, photoTest);
		photoTest.setDate(null);
		checkNotEquals(testPhoto, photoTest);
		photoTest.setDate(testPhoto.getDate());
		
		photoTest.setDescription("Some description");
		assertNotEquals(testPhoto, photoTest);
		photoTest.setDescription(null);
		checkNotEquals(photoTest, testPhoto);
		photoTest.setDescription(testPhoto.getDescription());
		
		photoTest.setTitle("title");
		assertNotEquals(testPhoto, photoTest);
		photoTest.setTitle(null);
		checkNotEquals(testPhoto, photoTest);
		photoTest.setTitle(testPhoto.getTitle());
		
		photoTest.setUrl("url");
		assertNotEquals(testPhoto, photoTest);
		photoTest.setUrl(null);
		checkNotEquals(testPhoto, photoTest);
		photoTest.setUrl(testPhoto.getUrl());

		photoTest.setThumbNailUrl("ThumbNailUrl");
		assertNotEquals(testPhoto, photoTest);
		photoTest.setThumbNailUrl(null);
		checkNotEquals(testPhoto, photoTest);
		photoTest.setThumbNailUrl(testPhoto.getThumbNailUrl());
}

}
