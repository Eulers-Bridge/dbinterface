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
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#PhotoDetails()}.
	 */
	@Test
	public final void testPhotoDetails1()
	{
		dets=new PhotoDetails();
		assertNotNull("Not yet implemented",dets);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#PhotoDetails(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)}.
	 */
	@Test
	public final void testPhotoDetails2()
	{
		dets=new PhotoDetails(testPhoto.getNodeId(), testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
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

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.photo.PhotoDetails#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		fail("Not yet implemented"); // TODO
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
