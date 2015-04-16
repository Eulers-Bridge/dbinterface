package com.eulersbridge.iEngage.core.events;

public class UpdatedEvent
{
	private Long nodeId;
	private Details details;
	protected boolean entityFound = true;
	protected boolean failed = false;

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

	/**
	 * @return the failed
	 */
	public boolean isFailed()
	{
		return failed;
	}

	public static UpdatedEvent notFound(Long id)
	{
		UpdatedEvent updatedEvent = new UpdatedEvent(id);
		updatedEvent.entityFound = false;
		return updatedEvent;
	}
	
	public static UpdatedEvent failed(Long id)
	{
		UpdatedEvent updatedEvent = new UpdatedEvent(id);
		updatedEvent.failed = true;
		return updatedEvent;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "UpdatedEvent [nodeId=" + nodeId + ", details=" + details
				+ ", entityFound=" + entityFound + ", failed=" + failed + "]";
	}
}
