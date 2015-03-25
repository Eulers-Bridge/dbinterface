/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import org.springframework.core.convert.converter.Converter;

import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;

/**
 * @author Greg Newitt
 *
 */

public class PhotoAlbumToOwnerConverter implements Converter<PhotoAlbum, Owner>
{

	@Override
	public Owner convert(PhotoAlbum source)
	{
		Owner owner=new Owner();
		owner.setNodeId(source.getNodeId());
			
		return owner;
	}

}
