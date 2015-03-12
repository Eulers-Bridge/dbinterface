/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 *
 */
public class CreateContactRequestEvent extends CreateEvent
{

	public CreateContactRequestEvent(ContactRequestDetails details)
	{
		super(details);
	}

}
