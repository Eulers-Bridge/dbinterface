package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 */

public class DeletePhotoAlbumEvent extends DeleteEvent
{
	public DeletePhotoAlbumEvent(Long eventId)
	{
		super(eventId);
	}
}
