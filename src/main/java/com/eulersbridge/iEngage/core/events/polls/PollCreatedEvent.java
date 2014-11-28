package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class PollCreatedEvent extends CreatedEvent
{
	private Long failedNodeId;
	private boolean ownerFound = true;
	private boolean creatorFound = true;

	public PollCreatedEvent(PollDetails pollDetails)
	{
		super(pollDetails);
	}

	public PollCreatedEvent(Long failedId)
	{
		this.failedNodeId = failedId;
	}

	/**
	 * @return the ownerFound
	 */
	public boolean isOwnerFound()
	{
		return ownerFound;
	}

	/**
	 * @param ownerFound the ownerFound to set
	 */
	public void setOwnerFound(boolean ownerFound)
	{
		this.ownerFound = ownerFound;
	}

	/**
	 * @return the failedNodeId
	 */
	public Long getFailedNodeId()
	{
		return failedNodeId;
	}

	/**
	 * @return the creatorFound
	 */
	public boolean isCreatorFound()
	{
		return creatorFound;
	}

	/**
	 * @param creatorFound the creatorFound to set
	 */
	public void setCreatorFound(boolean creatorFound)
	{
		this.creatorFound = creatorFound;
	}

	public static PollCreatedEvent ownerNotFound(Long ownerId)
	{
		PollCreatedEvent evt = new PollCreatedEvent(ownerId);
		evt.setOwnerFound(false);
		return evt;
	}
	
	public static PollCreatedEvent creatorNotFound(Long creatorId)
	{
		PollCreatedEvent evt = new PollCreatedEvent(creatorId);
		evt.setCreatorFound(false);
		return evt;
	}
}
