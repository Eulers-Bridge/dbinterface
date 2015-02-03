/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPhotosEvent extends ReadAllEvent
{
	public ReadPhotosEvent()
	{
		super(null);
	}

	public ReadPhotosEvent(Long ownerId)
	{
		super(ownerId);
	}
}
