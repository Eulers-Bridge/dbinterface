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
	private Long id;
	private boolean deletionCompleted=true;

	public VoteReminderDeletedEvent(Long id) 
	{
		this.id = id;
	}

	  public Long getVoteReminderId() 
	  {
	    return id;
	  }

	  public boolean isDeletionCompleted() 
	  {
	    return deletionCompleted;
	  }

	  public static VoteReminderDeletedEvent deletionForbidden(Long id) 
	  {
		  VoteReminderDeletedEvent ev = new VoteReminderDeletedEvent(id);
		  ev.entityFound=true;
		  ev.deletionCompleted=false;
		  return ev;
	  }

	  public static VoteReminderDeletedEvent notFound(Long id) 
	  {
		  VoteReminderDeletedEvent ev = new VoteReminderDeletedEvent(id);
		  ev.entityFound=false;
		  ev.deletionCompleted=false;
		  return ev;
	  }
}
