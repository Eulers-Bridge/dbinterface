package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class EventCreatedEvent extends CreatedEvent{
    private EventDetails eventDetails;
    private Long eventId;
	private boolean institutionFound=true;

    public EventCreatedEvent( Long eventId, EventDetails eventDetails)
    {
        this.eventDetails = eventDetails;
        this.eventId = eventId;
    }

    public EventCreatedEvent(Long eventId)
    {
        this.eventId = eventId;
    }

    public EventDetails getEventDetails()
    {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails)
    {
        this.eventDetails = eventDetails;
    }

    public Long getEventId()
    {
        return eventId;
    }

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound()
	{
		return institutionFound;
	}

	/**
	 * @param institutionFound the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound)
	{
		this.institutionFound = institutionFound;
	}

	public static EventCreatedEvent institutionNotFound(Long institutionId)
	{
		EventCreatedEvent evt=new EventCreatedEvent(institutionId);
		evt.setInstitutionFound(false);
		return evt;
	}
}
