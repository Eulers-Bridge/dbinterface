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
	public ReadVoteReminderEvent(Long id) 
	{
	    super(id);
	}
}
