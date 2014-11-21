package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Yikai Gong
 */

public class CreateElectionEvent extends CreateEvent
{
	public CreateElectionEvent(ElectionDetails electionDetails)
	{
		super(electionDetails);
	}
}
