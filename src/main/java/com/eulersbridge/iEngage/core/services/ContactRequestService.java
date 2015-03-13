/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
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

}
