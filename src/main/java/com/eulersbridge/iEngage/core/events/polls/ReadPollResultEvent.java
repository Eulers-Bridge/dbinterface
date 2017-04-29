/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPollResultEvent extends RequestReadEvent
{

	public ReadPollResultEvent(Long nodeId)
	{
		super(nodeId);
	}

}
