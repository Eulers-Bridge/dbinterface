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
import com.eulersbridge.iEngage.security.SecurityConstants;

/**
 * @author Greg Newitt
 *
 */
public interface ContactRequestService
{
	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	public CreatedEvent createContactRequest(CreateContactRequestEvent createContactRequestEvent);

	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	public ReadEvent readContactRequest(ReadContactRequestEvent readContactRequestEvent);

	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	public ReadEvent readContactRequestByUserIdContactNumber(ReadContactRequestEvent readContactRequestEvent);
	
	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	public UpdatedEvent acceptContactRequest(AcceptContactRequestEvent acceptContactRequestEvent);

	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	UpdatedEvent rejectContactRequest(UpdateEvent acceptContactRequestEvent);

	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readContactRequestsReceived(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);
	
	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readContactRequestsMade(ReadAllEvent readAllEvent, Direction sortDirection, int pageNumber, int pageLength);

}
