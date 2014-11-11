package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoUpdatedEvent extends UpdatedEvent
{
	private Long nodeId;
	private PhotoDetails photoDetails;

	public PhotoUpdatedEvent(Long nodeId,
			PhotoDetails photoDetails)
	{
		this.nodeId = nodeId;
		this.photoDetails = photoDetails;
	}

	public PhotoUpdatedEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public PhotoDetails getPhotoDetails()
	{
		return photoDetails;
	}

	public static PhotoUpdatedEvent notFound(Long id)
	{
		PhotoUpdatedEvent photoUpdatedEvent = new PhotoUpdatedEvent(
				id);
		photoUpdatedEvent.entityFound = false;
		return photoUpdatedEvent;
	}
}
