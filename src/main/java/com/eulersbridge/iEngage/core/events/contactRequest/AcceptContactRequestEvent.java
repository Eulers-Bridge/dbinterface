/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Greg Newitt
 *
 */
public class AcceptContactRequestEvent extends UpdateEvent
{

	public AcceptContactRequestEvent(Long nodeId, Details details)
	{
		super(nodeId, details);
	}

}
