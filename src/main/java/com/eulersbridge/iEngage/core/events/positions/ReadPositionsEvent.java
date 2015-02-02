/**
 * 
 */
package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPositionsEvent extends ReadAllEvent
{
	public ReadPositionsEvent()
	{
		super(null);
	}

	public ReadPositionsEvent(Long electionId)
	{
		super(electionId);
	}
}
