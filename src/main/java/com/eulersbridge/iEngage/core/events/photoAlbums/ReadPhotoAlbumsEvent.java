/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPhotoAlbumsEvent extends RequestReadEvent
{
	Long ownerId = null;

	public ReadPhotoAlbumsEvent()
	{
		super(null);
	}

	public ReadPhotoAlbumsEvent(Long ownerId)
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
