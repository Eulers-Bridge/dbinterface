package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadPollEvent extends ReadEvent
{
	public ReadPollEvent(Long pollId)
	{
		super(pollId);
	}

	public ReadPollEvent(Long pollId, PollDetails pollDetails)
	{
		super(pollId,pollDetails);
	}

	public Long getPollId()
	{
		return getNodeId();
	}

	public PollDetails getPollDetails()
	{
		return (PollDetails)getDetails();
	}
}
