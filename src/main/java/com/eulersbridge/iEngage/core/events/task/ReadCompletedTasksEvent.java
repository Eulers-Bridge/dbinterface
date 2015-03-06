/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadCompletedTasksEvent extends ReadAllEvent
{
	public ReadCompletedTasksEvent(Long userId)
	{
		super(userId);
	}
}
