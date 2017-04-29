/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import org.springframework.core.convert.converter.Converter;

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
