package com.eulersbridge.iEngage.core.events;

public class DeletedEvent
{
	protected Long nodeId;
	protected Details details;

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

	/**
	 * @param nodeId
	 * @param details
	 */
	public DeletedEvent(Long nodeId, Details details)
	{
		this(nodeId);
		this.details = details;
	}

	public static DeletedEvent deletionForbidden(Long nodeId)
	{
		DeletedEvent deletedEvent = new DeletedEvent(nodeId);
		deletedEvent.entityFound = true;
		deletedEvent.deletionCompleted = false;
		return deletedEvent;
	}

	public static DeletedEvent deletionForbidden(Long id, Details details)
	{
		DeletedEvent ev = deletionForbidden(id);
		ev.setDetails(details);
		return ev;
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

	/**
	 * @return the details
	 */
	public Details getDetails()
	{
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(Details details)
	{
		this.details = details;
	}
}
