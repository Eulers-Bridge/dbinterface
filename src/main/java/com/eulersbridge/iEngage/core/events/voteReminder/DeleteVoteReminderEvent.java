/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 *
 */
public class DeleteVoteReminderEvent extends DeleteEvent
{
	private final Long id;

	public DeleteVoteReminderEvent(final Long id) 
	{
		this.id = id;
	}

	public Long getVoteReminderId() 
	{
		return this.id;
	}

}
