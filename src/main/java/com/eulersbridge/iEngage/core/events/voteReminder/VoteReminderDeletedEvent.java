/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderDeletedEvent extends DeletedEvent
{
	public VoteReminderDeletedEvent(Long id) 
	{
		super(id);
	}

	  public Long getVoteReminderId() 
	  {
	    return getNodeId();
	  }
}
