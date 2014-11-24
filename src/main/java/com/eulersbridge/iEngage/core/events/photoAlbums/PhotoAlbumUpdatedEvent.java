package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumUpdatedEvent extends UpdatedEvent
{
	public PhotoAlbumUpdatedEvent(Long eventId,
			PhotoAlbumDetails photoAlbumDetails)
	{
		super(eventId,photoAlbumDetails);
	}

	public PhotoAlbumUpdatedEvent(Long eventId)
	{
		super(eventId);
	}
}
