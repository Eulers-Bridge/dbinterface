package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Newitt
 */

public class CreatePhotoEvent extends CreateEvent
{
    private PhotoDetails photoDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreatePhotoEvent.class);

    public CreatePhotoEvent(PhotoDetails photoDetails)
    {
        if (LOG.isDebugEnabled()) LOG.debug("CreateEvent("+photoDetails+") = ");
        this.photoDetails = photoDetails;
    }

    public PhotoDetails getPhotoDetails()
    {
        return photoDetails;
    }

    public void setPhotoDetails(PhotoDetails photoDetails)
    {
        this.photoDetails = photoDetails;
    }
}
