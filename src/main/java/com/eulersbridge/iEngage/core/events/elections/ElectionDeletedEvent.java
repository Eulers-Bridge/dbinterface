package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class ElectionDeletedEvent extends DeletedEvent
{
	public ElectionDeletedEvent(Long electionId)
	{
		super(electionId);
	}
}
