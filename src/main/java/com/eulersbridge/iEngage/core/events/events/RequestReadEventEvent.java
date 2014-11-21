package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadEventEvent extends RequestReadEvent
{
	public RequestReadEventEvent(Long eventId)
	{
		super(eventId);
	}
}
