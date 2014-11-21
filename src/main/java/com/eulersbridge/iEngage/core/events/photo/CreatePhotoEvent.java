package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 */

public class CreatePhotoEvent extends CreateEvent
{
    public CreatePhotoEvent(PhotoDetails photoDetails)
    {
        super(photoDetails);
    }
}
