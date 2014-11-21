/**
 * 
 */
package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadCountrysEvent extends RequestReadEvent
{

	public ReadCountrysEvent(Long nodeId)
	{
		super(nodeId);
	}

}
