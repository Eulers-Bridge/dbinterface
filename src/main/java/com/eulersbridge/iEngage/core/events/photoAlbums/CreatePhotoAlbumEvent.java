package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 */

public class CreatePhotoAlbumEvent extends CreateEvent
{
    public CreatePhotoAlbumEvent(PhotoAlbumDetails photoAlbumDetails)
    {
        super(photoAlbumDetails);
    }
}
