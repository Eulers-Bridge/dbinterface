package com.eulersbridge.iEngage.core.events;

public class UpdateEvent
{
	private Long nodeId;
	private Details details;

	public UpdateEvent(Long nodeId, Details details)
	{
		this.nodeId = nodeId;
		this.details = details;
		this.details.setNodeId(nodeId);
	}

	public Long getNodeId()
	{
		return this.nodeId;
	}

	public Details getDetails()
	{
		return this.details;
	}
}
