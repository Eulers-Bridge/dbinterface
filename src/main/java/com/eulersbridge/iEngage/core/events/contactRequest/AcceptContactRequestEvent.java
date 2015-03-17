/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class AcceptContactRequestEvent extends RequestReadEvent
{

	public AcceptContactRequestEvent(Long nodeId)
	{
		super(nodeId);
	}

}
