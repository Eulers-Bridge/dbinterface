/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadVoteReminderEvent extends RequestReadEvent
{
	private Long id;

	public ReadVoteReminderEvent(Long id) 
	{
	    this.id = id;
	}

	public Long getVoteReminderId() 
	{
	    return id;
	}
}
