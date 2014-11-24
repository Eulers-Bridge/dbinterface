package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateEventEvent extends UpdateEvent
{
	public UpdateEventEvent(Long eventId, EventDetails eventDetails)
	{
		super(eventId,eventDetails);
	}
}
