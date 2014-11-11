package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Greg Newitt
 */

public class UpdatePhotoEvent extends UpdateEvent
{
	private Long nodeId;
	private PhotoDetails photoDetails;

	public UpdatePhotoEvent(Long nodeId,
			PhotoDetails photoDetails)
	{
		this.nodeId = nodeId;
		this.photoDetails = photoDetails;
		this.photoDetails.setNodeId(nodeId);
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public PhotoDetails getPhotoDetails()
	{
		return photoDetails;
	}
}
