package com.eulersbridge.iEngage.core.events;

public class UpdatedEvent
{
	private Long nodeId;
	private Details details;
	protected boolean entityFound = true;

	public UpdatedEvent(Long nodeId, Details details)
	{
		this.nodeId = nodeId;
		this.details = details;
	}

	public UpdatedEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public Long getNodeId()
	{
		return this.nodeId;
	}

	public Details getDetails()
	{
		return this.details;
	}

	public boolean isEntityFound()
	{
		return entityFound;
	}

	public static UpdatedEvent notFound(Long id)
	{
		UpdatedEvent updatedEvent = new UpdatedEvent(id);
		updatedEvent.entityFound = false;
		return updatedEvent;
	}
}
