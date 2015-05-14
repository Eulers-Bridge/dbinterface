/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;

/**
 * @author Greg Newitt
 *
 */
public interface NotificationService
{

	CreatedEvent createNotification(CreateEvent createNotificationEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	ReadEvent readNotification(RequestReadEvent requestReadNotificationEvent);

	DeletedEvent deleteNotification(DeleteEvent deleteNotificationEvent);
	
	UpdatedEvent updateNotification(UpdateEvent updateNotificationEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	AllReadEvent readNotifications(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength);

}
