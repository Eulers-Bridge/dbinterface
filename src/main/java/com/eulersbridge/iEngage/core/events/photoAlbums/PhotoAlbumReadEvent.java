package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumReadEvent extends ReadEvent
{
	public PhotoAlbumReadEvent(Long photoAlbumId)
	{
		super(photoAlbumId);
	}

	public PhotoAlbumReadEvent(Long photoAlbumId, PhotoAlbumDetails photoAlbumDetails)
	{
		super(photoAlbumId,photoAlbumDetails);
	}
}
