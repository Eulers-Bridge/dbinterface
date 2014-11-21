package com.eulersbridge.iEngage.core.events;


public class DeletedEvent
{
	protected Long nodeId;
	protected boolean deletionCompleted = true;
	protected boolean entityFound = true;

	/**
	 * @param nodeId
	 */
	public DeletedEvent(Long nodeId)
	{
		super();
		this.nodeId = nodeId;
	}

	public static DeletedEvent deletionForbidden(Long nodeId)
	{
		DeletedEvent deletedEvent = new DeletedEvent(nodeId);
		deletedEvent.entityFound = true;
		deletedEvent.deletionCompleted = false;
		return deletedEvent;
	}

	public static DeletedEvent notFound(Long nodeId)
	{
		DeletedEvent deletedEvent = new DeletedEvent(nodeId);
		deletedEvent.entityFound = false;
		deletedEvent.deletionCompleted = false;
		return deletedEvent;
	}

	public boolean isEntityFound()
	{
		return entityFound;
	}

	public boolean isDeletionCompleted()
	{
		return this.deletionCompleted;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}
}
