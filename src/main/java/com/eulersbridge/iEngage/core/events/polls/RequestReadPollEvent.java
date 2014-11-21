package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadPollEvent extends RequestReadEvent
{
	public RequestReadPollEvent(Long pollId)
	{
		super(pollId);
	}
}
