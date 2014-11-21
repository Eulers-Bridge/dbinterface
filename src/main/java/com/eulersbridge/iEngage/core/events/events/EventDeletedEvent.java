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

	/**
	 * @return the eventId
	 */
	public Long getEventId()
	{
		return getNodeId();
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(Long eventId)
	{
		setNodeId(eventId);
	}

}
