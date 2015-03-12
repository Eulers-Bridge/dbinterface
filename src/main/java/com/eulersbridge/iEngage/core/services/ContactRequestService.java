/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

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
	public CreatedEvent createContactRequest(CreateContactRequestEvent createContactRequestEvent);

	public ReadEvent readContactRequest(ReadContactRequestEvent readContactRequestEvent);

	public ReadEvent readContactRequestByUserIdContactNumber(ReadContactRequestEvent readContactRequestEvent);

}
