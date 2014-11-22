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
	public DeleteVoteRecordEvent(final Long id) 
	{
		super(id);
	}
}
