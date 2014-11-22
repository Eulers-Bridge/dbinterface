package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadEventEvent extends ReadEvent
{
	public ReadEventEvent(Long eventId)
	{
		super(eventId);
	}

	public ReadEventEvent(Long eventId, EventDetails eventDetails)
	{
		super(eventId,eventDetails);
	}
}
