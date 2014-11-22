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
	public VoteRecordDeletedEvent(Long id) 
	{
		super(id);
	}
}
