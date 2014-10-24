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
	private Long id;
	private VoteReminderDetails voteReminderDetails;
	
	public VoteReminderReadEvent(Long id) 
	{
		this.id = id;
	}

	  public VoteReminderReadEvent(Long id, VoteReminderDetails voteReminderDetails) 
	  {
		  this.id = id;
		  this.voteReminderDetails = voteReminderDetails;
	  }

	  public Long getVoteReminderId() 
	  {
		  return this.id;
	  }

	  public VoteReminderDetails getVoteReminderDetails() 
	  {
		  return voteReminderDetails;
	  }

	  public static VoteReminderReadEvent notFound(Long id) 
	  {
		  VoteReminderReadEvent ev = new VoteReminderReadEvent(id);
		  ev.entityFound=false;
		  return ev;
	  }
}
