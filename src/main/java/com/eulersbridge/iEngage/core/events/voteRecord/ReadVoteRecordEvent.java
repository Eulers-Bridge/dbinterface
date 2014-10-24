/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadVoteRecordEvent extends RequestReadEvent 
{
	private Long id;

	public ReadVoteRecordEvent(Long id) 
	{
	    this.id = id;
	}

	public Long getVoteRecordId() 
	{
	    return id;
	}

}
