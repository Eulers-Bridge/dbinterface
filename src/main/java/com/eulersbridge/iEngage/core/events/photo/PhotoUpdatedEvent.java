package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoUpdatedEvent extends UpdatedEvent
{
	public PhotoUpdatedEvent(Long nodeId,
			PhotoDetails photoDetails)
	{
		super(nodeId,photoDetails);
	}

	public PhotoUpdatedEvent(Long nodeId)
	{
		super(nodeId);
	}
}
