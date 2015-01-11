/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class PollResultReadEvent extends ReadEvent
{
	public PollResultReadEvent(Long pollId,PollResultDetails dets)
	{
		super(pollId,dets);
	}

}
