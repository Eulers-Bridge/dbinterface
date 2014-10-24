/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class VoteRecordReadEvent extends ReadEvent
{
	private Long id;
	private VoteRecordDetails voteRecordDetails;
	
	public VoteRecordReadEvent(Long id) 
	{
		this.id = id;
	}

	  public VoteRecordReadEvent(Long id, VoteRecordDetails voteRecordDetails) 
	  {
		  this.id = id;
		  this.voteRecordDetails = voteRecordDetails;
	  }

	  public Long getVoteRecordId() 
	  {
		  return this.id;
	  }

	  public VoteRecordDetails getVoteRecordDetails() 
	  {
		  return voteRecordDetails;
	  }

	  public static VoteRecordReadEvent notFound(Long id) 
	  {
		  VoteRecordReadEvent ev = new VoteRecordReadEvent(id);
		  ev.entityFound=false;
		  return ev;
	  }
}
