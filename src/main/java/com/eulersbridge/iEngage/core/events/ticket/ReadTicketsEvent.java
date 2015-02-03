/**
 * 
 */
package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadTicketsEvent extends ReadAllEvent
{
	public ReadTicketsEvent()
	{
		super(null);
	}

	public ReadTicketsEvent(Long electionId)
	{
		super(electionId);
	}
}
