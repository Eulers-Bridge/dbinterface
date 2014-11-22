package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadElectionEvent extends ReadEvent
{
	public ReadElectionEvent(Long electionId)
	{
		super(electionId);
	}

	public ReadElectionEvent(Long electionId, ElectionDetails electionDetails)
	{
		super(electionId, electionDetails);
	}
}
