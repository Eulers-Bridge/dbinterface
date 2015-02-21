package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Newitt
 */

public class VotingLocationCreatedEvent extends CreatedEvent
{
	private Long votingLocationId;
    private boolean ownerFound=true;

	private static Logger LOG = LoggerFactory
			.getLogger(VotingLocationCreatedEvent.class);

	public VotingLocationCreatedEvent(Long badgeId)
	{
		if (LOG.isDebugEnabled()) LOG.debug("constructor()");
		this.votingLocationId = badgeId;
	}

	public VotingLocationCreatedEvent(VotingLocationDetails votingLocationDetails)
	{
		super(votingLocationDetails);
	}

	public VotingLocationCreatedEvent(Long votingLocationId,
			VotingLocationDetails votingLocationDetails)
	{
		super(votingLocationDetails);
		this.votingLocationId = votingLocationId;
	}

	public Long getVotingLocationId()
	{
		return votingLocationId;
	}

	public void setVotingLocationId(Long votingLocationId)
	{
		this.votingLocationId = votingLocationId;
	}

	/**
	 * @return the ownerFound
	 */
	public boolean isOwnerFound() {
		return ownerFound;
	}

	public static VotingLocationCreatedEvent ownerNotFound(Long ownerId) 
	{
		VotingLocationCreatedEvent evt=new VotingLocationCreatedEvent(ownerId);
		evt.ownerFound=false;
		return evt;
	}

}
