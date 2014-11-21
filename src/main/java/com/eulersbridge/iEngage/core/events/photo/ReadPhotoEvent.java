package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 */

public class ReadPhotoEvent extends RequestReadEvent
{
	public ReadPhotoEvent(Long nodeId)
	{
		super(nodeId);
	}
}
