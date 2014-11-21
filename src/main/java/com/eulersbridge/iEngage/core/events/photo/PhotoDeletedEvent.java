package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoDeletedEvent extends DeletedEvent
{
	public PhotoDeletedEvent(Long nodeId)
	{
		super(nodeId);
	}

}
