/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Greg Newitt
 *
 */
public class CompletedTaskEvent extends UpdateEvent
{

	public CompletedTaskEvent(Details details)
	{
		super(null, details);
	}

}
