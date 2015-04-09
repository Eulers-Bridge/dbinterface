/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;

/**
 * @author Greg Newitt
 *
 */
public interface ContactRequestService
{
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public CreatedEvent createContactRequest(CreateContactRequestEvent createContactRequestEvent);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadEvent readContactRequest(ReadContactRequestEvent readContactRequestEvent);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ReadEvent readContactRequestByUserIdContactNumber(ReadContactRequestEvent readContactRequestEvent);
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public UpdatedEvent acceptContactRequest(AcceptContactRequestEvent acceptContactRequestEvent);

	UpdatedEvent rejectContactRequest(UpdateEvent acceptContactRequestEvent);

	public AllReadEvent readContactRequestsReceived(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);
	
	public AllReadEvent readContactRequestsMade(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);

}
