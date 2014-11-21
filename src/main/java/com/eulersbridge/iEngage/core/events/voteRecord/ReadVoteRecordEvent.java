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
	public ReadVoteRecordEvent(Long id) 
	{
	    super(id);
	}
}
