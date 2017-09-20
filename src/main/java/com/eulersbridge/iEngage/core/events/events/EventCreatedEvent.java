package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class EventCreatedEvent extends CreatedEvent {
  private Long eventId;
  private boolean institutionFound = true;
  private boolean creatorFound = true;

  public EventCreatedEvent(Long eventId, EventDetails eventDetails) {
    super(eventDetails);
    this.eventId = eventId;
  }

  public EventCreatedEvent(Long eventId) {
    this.eventId = eventId;
  }

  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public boolean isCreatorFound() {
    return creatorFound;
  }

  public void setCreatorFound(boolean creatorFound) {
    this.creatorFound = creatorFound;
  }

  /**
   * @return the institutionFound
   */
  public boolean isInstitutionFound() {
    return institutionFound;
  }

  /**
   * @param institutionFound the institutionFound to set
   */
  public void setInstitutionFound(boolean institutionFound) {
    this.institutionFound = institutionFound;
  }

  public static EventCreatedEvent institutionNotFound(Long id) {
    EventCreatedEvent evt = new EventCreatedEvent(id);
    evt.setInstitutionFound(false);
    return evt;
  }

  public static EventCreatedEvent creatorNotFound(Long id) {
    EventCreatedEvent evt = new EventCreatedEvent(id);
    evt.setCreatorFound(false);
    return evt;
  }
}
