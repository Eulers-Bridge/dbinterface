package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumCreatedEvent extends CreatedEvent
{
	private boolean ownerFound = true;
	private boolean creatorFound = true;
	private Long ownerId;

	public PhotoAlbumCreatedEvent(PhotoAlbumDetails photoAlbumDetails)
	{	
		super(photoAlbumDetails);
	}

	public PhotoAlbumCreatedEvent(Long nodeId)
	{
		setOwnerId(nodeId);
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isOwnerFound()
	{
		return ownerFound;
	}

	/**
	 * @param institutionFound
	 *            the institutionFound to set
	 */
	public void setOwnerFound(boolean ownerFound)
	{
		this.ownerFound = ownerFound;
	}

	public static PhotoAlbumCreatedEvent ownerNotFound(Long ownerId)
	{
		PhotoAlbumCreatedEvent evt = new PhotoAlbumCreatedEvent(ownerId);
		evt.setOwnerFound(false);
		return evt;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
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

	public static PhotoAlbumCreatedEvent creatorNotFound(Long creatorId)
	{
		PhotoAlbumCreatedEvent evt = new PhotoAlbumCreatedEvent(creatorId);
		evt.setCreatorFound(false);
		return evt;
	}
}
