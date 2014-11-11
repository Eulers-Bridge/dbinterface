package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 */

public class DeletePhotoEvent extends DeleteEvent
{
	private final Long nodeId;

	public DeletePhotoEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public Long getNodeId()
	{
		return nodeId;
	}
}
