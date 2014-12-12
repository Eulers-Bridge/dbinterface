package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumUpdatedEvent extends UpdatedEvent
{
	private boolean ownerFound = true;
	private boolean creatorFound = true;

	public PhotoAlbumUpdatedEvent(Long eventId,
			PhotoAlbumDetails photoAlbumDetails)
	{
		super(eventId,photoAlbumDetails);
	}

	public PhotoAlbumUpdatedEvent(Long eventId)
	{
		super(eventId);
	}

	/**
	 * @return the ownerFound
	 */
	public boolean isOwnerFound()
	{
		return ownerFound;
	}

	/**
	 * @param ownerFound the ownerFound to set
	 */
	public void setOwnerFound(boolean ownerFound)
	{
		this.ownerFound = ownerFound;
	}

	/**
	 * @return the creatorFound
	 */
	public boolean isCreatorFound()
	{
		return creatorFound;
	}

	/**
	 * @param creatorFound the creatorFound to set
	 */
	public void setCreatorFound(boolean creatorFound)
	{
		this.creatorFound = creatorFound;
	}
	
	public static PhotoAlbumCreatedEvent ownerNotFound(Long ownerId)
	{
		PhotoAlbumCreatedEvent evt = new PhotoAlbumCreatedEvent(ownerId);
		evt.setOwnerFound(false);
		return evt;
	}

	public static PhotoAlbumCreatedEvent creatorNotFound(Long creatorId)
	{
		PhotoAlbumCreatedEvent evt = new PhotoAlbumCreatedEvent(creatorId);
		evt.setCreatorFound(false);
		return evt;
	}
}
