/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPollsEvent extends RequestReadEvent
{
	Long ownerId = null;

	public ReadPollsEvent()
	{
		super(null);
	}

	public ReadPollsEvent(Long ownerId)
	{
		super(null);
		this.ownerId = ownerId;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

}
