/**
 * 
 */
package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadElectionsEvent extends ReadAllEvent 
{
	public ReadElectionsEvent()
	{
		super(null);
	}

	public ReadElectionsEvent(Long institutionId)
	{
		super(institutionId);
	}
}
