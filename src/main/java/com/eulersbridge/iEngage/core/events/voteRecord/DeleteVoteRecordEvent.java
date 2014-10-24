/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 *
 */
public class DeleteVoteRecordEvent extends DeleteEvent
{
	private final Long id;

	public DeleteVoteRecordEvent(final Long id) 
	{
		this.id = id;
	}

	public Long getVoteRecordId() 
	{
		return this.id;
	}

}
