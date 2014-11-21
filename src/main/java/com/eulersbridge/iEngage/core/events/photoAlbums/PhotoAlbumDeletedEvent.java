package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumDeletedEvent extends DeletedEvent
{
	public PhotoAlbumDeletedEvent(Long nodeId)
	{
		super(nodeId);
	}

}
