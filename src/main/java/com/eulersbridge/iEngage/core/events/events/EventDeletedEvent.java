package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class EventDeletedEvent extends DeletedEvent
{
	public EventDeletedEvent(Long eventId)
	{
		super(eventId);
	}
}
