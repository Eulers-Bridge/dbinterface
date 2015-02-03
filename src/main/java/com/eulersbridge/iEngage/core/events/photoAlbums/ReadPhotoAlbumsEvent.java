/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPhotoAlbumsEvent extends ReadAllEvent
{
	public ReadPhotoAlbumsEvent()
	{
		super(null);
	}

	public ReadPhotoAlbumsEvent(Long ownerId)
	{
		super(ownerId);
	}
}
