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
	public VoteRecordReadEvent(Long id) 
	{
		super(id);
	}

	  public VoteRecordReadEvent(Long id, VoteRecordDetails voteRecordDetails) 
	  {
		  super(id,voteRecordDetails);
	  }

	  public Long getVoteRecordId() 
	  {
		  return getNodeId();
	  }

	  public VoteRecordDetails getVoteRecordDetails() 
	  {
		  return (VoteRecordDetails)getDetails();
	  }
}
