/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

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
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#Photo(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)}.
	 */
	@Test
	public final void testPhoto2()
	{
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertNotNull("Not yet implemented",testPhoto2);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
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
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertNull(testPhoto2.getNodeId());
		testPhoto2.setNodeId(nodeId);
		assertEquals(nodeId,testPhoto2.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getUrl()}.
	 */
	@Test
	public final void testGetUrl()
	{
		String url=testPhoto.getUrl();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertEquals(url,testPhoto2.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setURL(java.lang.String)}.
	 */
	@Test
	public final void testSetURL()
	{
		String url=testPhoto.getUrl();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertEquals(url,testPhoto2.getUrl());
		url="testUrl";
		testPhoto2.setURL(url);
		assertEquals(url,testPhoto2.getUrl());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#getTitle()}.
	 */
	@Test
	public final void testGetTitle()
	{
		String title=testPhoto.getTitle();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertEquals(title,testPhoto2.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle()
	{
		String title=testPhoto.getTitle();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
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
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertEquals(description,testPhoto2.getDescription());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		String description=testPhoto.getDescription();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
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
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertEquals(date,testPhoto2.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetDate()
	{
		Long date=testPhoto.getDate();
		testPhoto2=new Photo(testPhoto.getUrl(), testPhoto.getTitle(), testPhoto.getDescription(), testPhoto.getDate());
		assertEquals(date,testPhoto2.getDate());
		date=4949494l;
		testPhoto2.setDate(date);
		assertEquals(date,testPhoto2.getDate());
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
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#fromPhotoDetails(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}.
	 */
	@Test
	public final void testFromPhotoDetails()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Photo#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		fail("Not yet implemented"); // TODO
	}

}
