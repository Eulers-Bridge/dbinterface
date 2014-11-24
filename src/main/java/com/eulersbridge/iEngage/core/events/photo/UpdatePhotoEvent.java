package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Greg Newitt
 */

public class UpdatePhotoEvent extends UpdateEvent
{
	public UpdatePhotoEvent(Long nodeId,
			PhotoDetails photoDetails)
	{
		super(nodeId,photoDetails);
	}
}
