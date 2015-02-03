/**
 * 
 */
package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadCandidatesEvent extends ReadAllEvent
{
	
	public ReadCandidatesEvent()
	{
		super(null);
	}

	public ReadCandidatesEvent(Long electionId)
	{
		super(electionId);
	}
}
