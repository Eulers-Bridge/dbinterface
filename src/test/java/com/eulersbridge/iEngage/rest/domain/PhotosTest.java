/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class PhotosTest
{
	Photos fotos;
	private Integer totalPages;
	private Long totalPhotos;
	private Iterator<Photo> photos;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		totalPages=1;
		totalPhotos=1l;
		photos=null;
		fotos=new Photos();
		fotos.setTotalPages(totalPages);
		fotos.setTotalPhotos(totalPhotos);
		fotos.setPhotos(photos);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#getTotalPhotos()}.
	 */
	@Test
	public final void testGetTotalPhotos()
	{
		assertEquals(fotos.getTotalPhotos(),totalPhotos);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#setTotalPhotos(java.lang.Long)}.
	 */
	@Test
	public final void testSetTotalPhotos()
	{
		totalPhotos++;
		assertNotEquals(fotos.getTotalPhotos(),totalPhotos);
		fotos.setTotalPhotos(totalPhotos);
		assertEquals(fotos.getTotalPhotos(),totalPhotos);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#getTotalPages()}.
	 */
	@Test
	public final void testGetTotalPages()
	{
		assertEquals(fotos.getTotalPages(),totalPages);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#setTotalPages(java.lang.Integer)}.
	 */
	@Test
	public final void testSetTotalPages()
	{
		totalPages++;
		assertNotEquals(fotos.getTotalPages(),totalPages);
		fotos.setTotalPages(totalPages);
		assertEquals(fotos.getTotalPages(),totalPages);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#getPhotos()}.
	 */
	@Test
	public final void testGetPhotos()
	{
		assertEquals(fotos.getPhotos(),photos);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#setPhotos(java.util.Iterator)}.
	 */
	@Test
	public final void testSetPhotos()
	{
		Iterator<Photo> fotes=new ArrayList<Photo>().iterator();
		assertNotEquals(fotos.getPhotos(),fotes);
		fotos.setPhotos(fotes);
		assertEquals(fotos.getPhotos(),fotes);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Photos#fromPhotosIterator(java.util.Iterator, java.lang.Long, java.lang.Integer)}.
	 */
	@Ignore
	@Test
	public final void testFromPhotosIterator()
	{
		fail("Not yet implemented"); // TODO
	}

}
