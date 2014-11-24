package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class ElectionUpdatedEvent extends UpdatedEvent
{
	public ElectionUpdatedEvent(Long electionId, ElectionDetails electionDetails)
	{
		super(electionId, electionDetails);
	}

	public ElectionUpdatedEvent(Long electionId)
	{
		super(electionId);
	}
}
