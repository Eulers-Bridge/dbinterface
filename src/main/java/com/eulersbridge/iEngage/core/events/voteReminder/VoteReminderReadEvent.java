/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderReadEvent extends ReadEvent
{
	public VoteReminderReadEvent(Long id) 
	{
		super(id);
	}

	  public VoteReminderReadEvent(Long id, VoteReminderDetails voteReminderDetails) 
	  {
		  super(id,voteReminderDetails);
	  }

	  public Long getVoteReminderId() 
	  {
		  return getNodeId();
	  }

	  public VoteReminderDetails getVoteReminderDetails() 
	  {
		  return (VoteReminderDetails)getDetails();
	  }
}
