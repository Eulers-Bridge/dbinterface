package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumDeletedEvent extends DeletedEvent
{
	private Long nodeId;
	private boolean deletionCompleted = true;

	public PhotoAlbumDeletedEvent(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public static PhotoAlbumDeletedEvent deletionForbidden(Long nodeId)
	{
		PhotoAlbumDeletedEvent photoAlbumDeletedEvent = new PhotoAlbumDeletedEvent(
				nodeId);
		photoAlbumDeletedEvent.entityFound = true;
		photoAlbumDeletedEvent.deletionCompleted = false;
		return photoAlbumDeletedEvent;
	}

	public static PhotoAlbumDeletedEvent notFound(Long nodeId)
	{
		PhotoAlbumDeletedEvent photoAlbumDeletedEvent = new PhotoAlbumDeletedEvent(
				nodeId);
		photoAlbumDeletedEvent.entityFound = false;
		photoAlbumDeletedEvent.deletionCompleted = false;
		return photoAlbumDeletedEvent;
	}

	public boolean isDeletionCompleted()
	{
		return this.deletionCompleted;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

}
