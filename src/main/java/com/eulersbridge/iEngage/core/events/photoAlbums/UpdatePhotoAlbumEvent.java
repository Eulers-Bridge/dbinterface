package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Greg Newitt
 */

public class UpdatePhotoAlbumEvent extends UpdateEvent
{
	public UpdatePhotoAlbumEvent(Long nodeId,
			PhotoAlbumDetails photoAlbumDetails)
	{
		super(nodeId,photoAlbumDetails);
	}
}
