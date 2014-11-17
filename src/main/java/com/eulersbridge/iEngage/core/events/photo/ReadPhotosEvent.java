/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPhotosEvent extends RequestReadEvent
{
	Long ownerId=null;
	
	public ReadPhotosEvent()
	{

	}

	public ReadPhotosEvent(Long ownerId)
	{
		this.ownerId=ownerId;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

}
