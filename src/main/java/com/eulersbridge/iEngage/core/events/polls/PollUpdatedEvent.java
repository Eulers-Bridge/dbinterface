package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class PollUpdatedEvent extends UpdatedEvent
{
	private boolean ownerFound = true;
	private boolean creatorFound = true;

	public PollUpdatedEvent(Long pollId, PollDetails pollDetails)
	{
		super(pollId, pollDetails);
	}

	public PollUpdatedEvent(Long pollId)
	{
		super(pollId);
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

	public static PollUpdatedEvent ownerNotFound(Long ownerId)
	{
		PollUpdatedEvent evt = new PollUpdatedEvent(ownerId);
		evt.setOwnerFound(false);
		evt.entityFound=false;
		return evt;
	}
	
	public static PollUpdatedEvent creatorNotFound(Long creatorId)
	{
		PollUpdatedEvent evt = new PollUpdatedEvent(creatorId);
		evt.setCreatorFound(false);
		evt.entityFound=false;
		return evt;
	}
}
