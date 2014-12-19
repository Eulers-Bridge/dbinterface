/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 *
 */
public class CreatePollAnswerEvent extends CreateEvent
{
	public CreatePollAnswerEvent(PollAnswerDetails pollAnswerDetails)
	{
		super(pollAnswerDetails);
	}

}
