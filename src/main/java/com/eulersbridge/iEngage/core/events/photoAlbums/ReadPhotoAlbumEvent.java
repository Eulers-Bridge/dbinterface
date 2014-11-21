package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 */

public class ReadPhotoAlbumEvent extends RequestReadEvent
{
	public ReadPhotoAlbumEvent(Long nodeId)
	{
		super(nodeId);
	}
}
