/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class PhotoAlbumToOwnerConverterTest
{
	PhotoAlbumToOwnerConverter converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		converter=new PhotoAlbumToOwnerConverter();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.converters.PhotoAlbumToOwnerConverter#convert(com.eulersbridge.iEngage.database.domain.PhotoAlbum)}.
	 */
	@Test
	public final void testConvert()
	{
		PhotoAlbum source=DatabaseDataFixture.populatePhotoAlbum1();		
		Owner owner=converter.convert(source);
		assertEquals(owner.getNodeId(),source.getNodeId());
	}

}