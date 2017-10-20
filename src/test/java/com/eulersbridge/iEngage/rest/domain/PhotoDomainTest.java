/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PhotoDomainTest
{

	@Mock
	private ServletRequestAttributes attrs;
	
	PhotoDomain photo;
	PhotoDetails photoDetails;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request=new MockHttpServletRequest();
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		photoDetails=DatabaseDataFixture.populatePhoto1().toPhotoDetails();
		photo= PhotoDomain.fromPhotoDetails(photoDetails);
	}

	/**
	 * Test method for {@link PhotoDomain#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(photoDetails.getNodeId(),photo.getNodeId());
	}

	/**
	 * Test method for {@link PhotoDomain#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		assertEquals(photoDetails.getNodeId(),photo.getNodeId());
		Long nodeId=44444l;
		assertNotEquals(nodeId,photo.getNodeId());
		photo.setNodeId(nodeId);
		assertEquals(nodeId,photo.getNodeId());
	}

	/**
	 * Test method for {@link PhotoDomain#getUrl()}.
	 */
	@Test
	public final void testGetUrl()
	{
		assertEquals(photoDetails.getUrl(),photo.getUrl());
	}

	/**
	 * Test method for {@link PhotoDomain#setUrl(java.lang.String)}.
	 */
	@Test
	public final void testSetUrl()
	{
		assertEquals(photoDetails.getUrl(),photo.getUrl());
		String url="Some different title.";
		assertNotEquals(url,photo.getUrl());
		photo.setUrl(url);;
		assertEquals(url,photo.getUrl());
	}

	/**
	 * Test method for {@link PhotoDomain#getUrl()}.
	 */
	@Test
	public final void testGetThumbNailUrl()
	{
		assertEquals(photoDetails.getThumbNailUrl(),photo.getThumbNailUrl());
	}

	/**
	 * Test method for {@link PhotoDomain#setUrl(java.lang.String)}.
	 */
	@Test
	public final void testSetThumbNailUrl()
	{
		assertEquals(photoDetails.getThumbNailUrl(),photo.getThumbNailUrl());
		String url="Some different ThumbNail title.";
		assertNotEquals(url,photo.getThumbNailUrl());
		photo.setThumbNailUrl(url);;
		assertEquals(url,photo.getThumbNailUrl());
	}

	/**
	 * Test method for {@link PhotoDomain#getTitle()}.
	 */
	@Test
	public final void testGetTitle()
	{
		assertEquals(photoDetails.getTitle(),photo.getTitle());
	}

	/**
	 * Test method for {@link PhotoDomain#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle()
	{
		assertEquals(photoDetails.getTitle(),photo.getTitle());
		String title="Some different title.";
		assertNotEquals(title,photo.getTitle());
		photo.setTitle(title);;
		assertEquals(title,photo.getTitle());
	}

	/**
	 * Test method for {@link PhotoDomain#getDescription()}.
	 */
	@Test
	public final void testGetDescription()
	{
		assertEquals(photoDetails.getDescription(),photo.getDescription());
	}

	/**
	 * Test method for {@link PhotoDomain#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription()
	{
		assertEquals(photoDetails.getDescription(),photo.getDescription());
		String description="Some different description.";
		assertNotEquals(description,photo.getDescription());
		photo.setDescription(description);
		assertEquals(description,photo.getDescription());
	}

	/**
	 * Test method for {@link PhotoDomain#getDate()}.
	 */
	@Test
	public final void testGetDate()
	{
		assertEquals(photoDetails.getDate(),photo.getDate());
	}

	/**
	 * Test method for {@link PhotoDomain#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetDate()
	{
		assertEquals(photoDetails.getDate(),photo.getDate());
		Long date=43234l;
		assertNotEquals(date,photo.getDate());
		photo.setDate(date);
		assertEquals(date,photo.getDate());
	}

	/**
	 * Test method for {@link PhotoDomain#getOwnerId()}.
	 */
	@Test
	public final void testGetOwnerId()
	{
		assertEquals(photoDetails.getOwnerId(),photo.getOwnerId());
	}

	/**
	 * Test method for {@link PhotoDomain#setOwnerId(java.lang.Long)}.
	 */
	@Test
	public final void testSetOwnerId()
	{
		assertEquals(photoDetails.getOwnerId(),photo.getOwnerId());
		Long ownerId=43234l;
		assertNotEquals(ownerId,photo.getOwnerId());
		photo.setOwnerId(ownerId);
		assertEquals(ownerId,photo.getOwnerId());
	}

	/**
	 * Test method for {@link PhotoDomain#getOwnerId()}.
	 */
	@Test
	public final void testGetSequence()
	{
		assertEquals(photoDetails.getSequence(),photo.getSequence());
	}

	/**
	 * Test method for {@link PhotoDomain#setOwnerId(java.lang.Long)}.
	 */
	@Test
	public final void testSetSequence()
	{
		assertEquals(photoDetails.getSequence(),photo.getSequence());
		Integer sequence=43234;
		assertNotEquals(sequence,photo.getSequence());
		photo.setSequence(sequence);
		assertEquals(sequence,photo.getSequence());
	}

	/**
	 * Test method for {@link PhotoDomain#fromPhotoDetails(com.eulersbridge.iEngage.core.events.photo.PhotoDetails)}.
	 */
	@Test
	public final void testFromPhotoDetails()
	{
		assertEquals(photoDetails.getDate(),photo.getDate());
		assertEquals(photoDetails.getDescription(),photo.getDescription());
		assertEquals(photoDetails.getNodeId(),photo.getNodeId());
		assertEquals(photoDetails.getTitle(),photo.getTitle());
		assertEquals(photoDetails.getUrl(),photo.getUrl());
		assertEquals(photoDetails.getThumbNailUrl(),photo.getThumbNailUrl());
		assertEquals(photoDetails.getSequence(),photo.getSequence());
	}

	/**
	 * Test method for {@link PhotoDomain#toPhotoDetails()}.
	 */
	@Test
	public final void testToPhotoDetails()
	{
		photoDetails=photo.toPhotoDetails();
		assertEquals(photoDetails.getDate(),photo.getDate());
		assertEquals(photoDetails.getDescription(),photo.getDescription());
		assertEquals(photoDetails.getNodeId(),photo.getNodeId());
		assertEquals(photoDetails.getTitle(),photo.getTitle());
		assertEquals(photoDetails.getUrl(),photo.getUrl());
		assertEquals(photoDetails.getThumbNailUrl(),photo.getThumbNailUrl());
		assertEquals(photoDetails.getSequence(),photo.getSequence());
	}

	/**
	 * Test method for {@link PhotoDomain#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull(photo.toString());
	}

}
