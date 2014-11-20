package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoCreatedEvent extends CreatedEvent
{
    private PhotoDetails photoDetails;
    private Long nodeId;
	private boolean ownerFound=true;

    public PhotoCreatedEvent( Long nodeId, PhotoDetails photoDetails)
    {
        this.photoDetails = photoDetails;
        this.nodeId = nodeId;
    }

    public PhotoCreatedEvent(Long nodeId)
    {
        this.nodeId = nodeId;
    }

@Override
    public PhotoDetails getDetails()
    {
        return photoDetails;
    }

    public PhotoDetails getPhotoDetails()
    {
        return photoDetails;
    }

    public void setPhotoDetails(PhotoDetails photoDetails)
    {
        this.photoDetails = photoDetails;
    }

    public Long getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(Long nodeId)
    {
        this.nodeId = nodeId;
    }

	/**
	 * @return the institutionFound
	 */
	public boolean isOwnerFound()
	{
		return ownerFound;
	}

	/**
	 * @param institutionFound the institutionFound to set
	 */
	public void setOwnerFound(boolean ownerFound)
	{
		this.ownerFound = ownerFound;
	}

	public static PhotoCreatedEvent ownerNotFound(Long ownerId)
	{
		PhotoCreatedEvent evt=new PhotoCreatedEvent(ownerId);
		evt.setOwnerFound(false);
		return evt;
	}
}
