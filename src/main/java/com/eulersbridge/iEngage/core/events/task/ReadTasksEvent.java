/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadTasksEvent extends ReadAllEvent
{
	public ReadTasksEvent()
	{
		super(null);
	}

	public ReadTasksEvent(Long electionId)
	{
		super(electionId);
	}
}
