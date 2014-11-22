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
	public DeleteVoteReminderEvent(final Long id) 
	{
		super(id);
	}
}
