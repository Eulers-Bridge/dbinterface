package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoCreatedEvent extends CreatedEvent
{
    private Long nodeId;
	private boolean ownerFound=true;

    public PhotoCreatedEvent( Long nodeId, PhotoDetails photoDetails)
    {
        super(photoDetails);
        this.nodeId = nodeId;
    }

    public PhotoCreatedEvent(Long nodeId)
    {
        this.nodeId = nodeId;
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
