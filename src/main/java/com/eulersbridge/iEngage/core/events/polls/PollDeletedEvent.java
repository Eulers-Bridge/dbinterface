package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class PollDeletedEvent extends DeletedEvent
{

	public PollDeletedEvent(Long pollid)
	{
		super(pollid);
	}
}
