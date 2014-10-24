/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 *
 */
public class VoteRecordDeletedEvent extends DeletedEvent
{
	private Long id;
	private boolean deletionCompleted=true;

	public VoteRecordDeletedEvent(Long id) 
	{
		this.id = id;
	}

	  public Long getVoteRecordId() 
	  {
	    return id;
	  }

	  public boolean isDeletionCompleted() 
	  {
	    return deletionCompleted;
	  }

	  public static VoteRecordDeletedEvent deletionForbidden(Long id) 
	  {
		  VoteRecordDeletedEvent ev = new VoteRecordDeletedEvent(id);
		  ev.entityFound=true;
		  ev.deletionCompleted=false;
		  return ev;
	  }

	  public static VoteRecordDeletedEvent notFound(Long id) 
	  {
		  VoteRecordDeletedEvent ev = new VoteRecordDeletedEvent(id);
		  ev.entityFound=false;
		  ev.deletionCompleted=false;
		  return ev;
	  }
}
