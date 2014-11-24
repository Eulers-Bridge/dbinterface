package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class PollUpdatedEvent extends UpdatedEvent
{
	public PollUpdatedEvent(Long pollId, PollDetails pollDetails)
	{
		super(pollId, pollDetails);
	}

	public PollUpdatedEvent(Long pollId)
	{
		super(pollId);
	}
}
