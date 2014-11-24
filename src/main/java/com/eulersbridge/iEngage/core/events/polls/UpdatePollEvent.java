package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdatePollEvent extends UpdateEvent
{
	public UpdatePollEvent(Long pollId, PollDetails pollDetails)
	{
		super(pollId, pollDetails);
	}
}
