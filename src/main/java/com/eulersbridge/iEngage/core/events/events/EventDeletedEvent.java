package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class EventDeletedEvent extends DeletedEvent{
    private Long eventId;
    private boolean deletionCompleted = true;

    public EventDeletedEvent(Long eventId) {
        this.eventId = eventId;
    }

    public static EventDeletedEvent deletionForbidden(Long eventId){
        EventDeletedEvent eventDeletedEvent = new EventDeletedEvent(eventId);
        eventDeletedEvent.entityFound = true;
        eventDeletedEvent.deletionCompleted = false;
        return eventDeletedEvent;
    }

    public static EventDeletedEvent notFound(Long eventId){
        EventDeletedEvent eventDeletedEvent = new EventDeletedEvent(eventId);
        eventDeletedEvent.entityFound = false;
        eventDeletedEvent.deletionCompleted = false;
        return eventDeletedEvent;
    }

    public boolean isDeletionCompleted(){
        return this.deletionCompleted;
    }

	/**
	 * @return the eventId
	 */
	public Long getEventId()
	{
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Long eventId)
	{
		this.eventId = eventId;
	}

}
