package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class EventUpdatedEvent extends UpdatedEvent
{
	public EventUpdatedEvent(Long eventId, EventDetails eventDetails)
	{
		super(eventId,eventDetails);
	}

	public EventUpdatedEvent(Long eventId)
	{
		super(eventId);
	}
}
