package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.events.*;
import com.eulersbridge.iEngage.rest.domain.EventDomain;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface EventService {

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public RequestHandledEvent createEvent(EventDomain eventDomain);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readEvent(RequestReadEventEvent requestReadEventEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updateEvent(UpdateEventEvent updateEventEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.CONTENT_MANAGER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deleteEvent(DeleteEventEvent deleteEventEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public AllReadEvent readEvents(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);
}
