/**
 * 
 */
package com.eulersbridge.iEngage.core.events.events;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadEventsEvent extends RequestReadEvent
{
	Long instId;
	Long nfId;
	
	private ReadEventsEvent()
	{
		super();
	}
	
	public ReadEventsEvent(Long institutionId)
	{
		this();
		this.instId=institutionId;
	}

	/**
	 * @return the instId
	 */
	public Long getInstId() {
		return instId;
	}

	/**
	 * @param instId the instId to set
	 */
	public void setInstId(Long instId) {
		this.instId = instId;
	}

}
