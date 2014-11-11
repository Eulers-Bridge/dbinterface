package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 */

public class ReadPhotoEvent extends RequestReadEvent
{
	private Long nodeId;

	public ReadPhotoEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public Long getNodeId()
	{
		return nodeId;
	}
}
