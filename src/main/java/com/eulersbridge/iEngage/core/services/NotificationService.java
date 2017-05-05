/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Greg Newitt
 */
public interface NotificationService {

  CreatedEvent createNotification(CreateEvent createNotificationEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  ReadEvent readNotification(RequestReadEvent requestReadNotificationEvent);

  DeletedEvent deleteNotification(DeleteEvent deleteNotificationEvent);

  UpdatedEvent updateNotification(UpdateEvent updateNotificationEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  AllReadEvent readNotifications(ReadAllEvent readAllEvent,
                                 Direction sortDirection, int pageNumber, int pageLength);

}
