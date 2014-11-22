package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 */

public class PhotoReadEvent extends ReadEvent
{
	public PhotoReadEvent(Long nodeId)
	{
		super(nodeId);
	}

	public PhotoReadEvent(Long nodeId, PhotoDetails photoDetails)
	{
		super(nodeId,photoDetails);
	}
}
