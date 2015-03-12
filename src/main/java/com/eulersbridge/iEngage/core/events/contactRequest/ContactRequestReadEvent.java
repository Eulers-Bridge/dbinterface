/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ContactRequestReadEvent extends ReadEvent
{

	public ContactRequestReadEvent(Long nodeId)
	{
		super(nodeId);
		// TODO Auto-generated constructor stub
	}

	public ContactRequestReadEvent(Long nodeId, Details details)
	{
		super(nodeId, details);
		// TODO Auto-generated constructor stub
	}

}
