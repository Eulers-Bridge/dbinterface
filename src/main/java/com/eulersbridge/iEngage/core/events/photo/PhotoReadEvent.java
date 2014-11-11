package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 */

public class PhotoReadEvent extends ReadEvent
{
	private Long nodeId;
	private PhotoDetails photoDetails;

	public PhotoReadEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public PhotoReadEvent(Long nodeId, PhotoDetails photoDetails)
	{
		this.nodeId = nodeId;
		this.photoDetails = photoDetails;
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public PhotoDetails getPhotoDetails()
	{
		return photoDetails;
	}

	public static PhotoReadEvent notFound(Long nodeId)
	{
		PhotoReadEvent photoReadEvent = new PhotoReadEvent(
				nodeId);
		photoReadEvent.entityFound = false;
		return photoReadEvent;
	}
}
