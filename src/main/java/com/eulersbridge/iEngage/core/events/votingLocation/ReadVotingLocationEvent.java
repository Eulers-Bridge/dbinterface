package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 */

public class ReadVotingLocationEvent extends RequestReadEvent
{
	public ReadVotingLocationEvent(Long badgeId)
	{
		super(badgeId);
	}
}
