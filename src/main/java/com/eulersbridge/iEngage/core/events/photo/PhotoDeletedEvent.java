package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoDeletedEvent extends DeletedEvent
{
	private Long nodeId;
	private boolean deletionCompleted = true;

	public PhotoDeletedEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public static PhotoDeletedEvent deletionForbidden(Long nodeId)
	{
		PhotoDeletedEvent photoDeletedEvent = new PhotoDeletedEvent(nodeId);
		photoDeletedEvent.entityFound = true;
		photoDeletedEvent.deletionCompleted = false;
		return photoDeletedEvent;
	}

	public static PhotoDeletedEvent notFound(Long nodeId)
	{
		PhotoDeletedEvent photoDeletedEvent = new PhotoDeletedEvent(nodeId);
		photoDeletedEvent.entityFound = false;
		photoDeletedEvent.deletionCompleted = false;
		return photoDeletedEvent;
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
