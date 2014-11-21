package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Greg Newitt
 */

public class DeletePhotoEvent extends DeleteEvent
{
	public DeletePhotoEvent(Long nodeId)
	{
		super(nodeId);
	}
}
