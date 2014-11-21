package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumCreatedEvent extends CreatedEvent
{
	private boolean institutionFound = true;

	public PhotoAlbumCreatedEvent(PhotoAlbumDetails photoAlbumDetails)
	{	
		super(photoAlbumDetails);
	}

	public PhotoAlbumCreatedEvent(Long eventId)
	{
	}

@Override
	public PhotoAlbumDetails getDetails()
	{
		return (PhotoAlbumDetails)super.getDetails();
	}

	public void setDetails(PhotoAlbumDetails photoAlbumDetails)
	{
		super.setDetails(photoAlbumDetails);
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound()
	{
		return institutionFound;
	}

	/**
	 * @param institutionFound
	 *            the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound)
	{
		this.institutionFound = institutionFound;
	}

	public static PhotoAlbumCreatedEvent institutionNotFound(Long institutionId)
	{
		PhotoAlbumCreatedEvent evt = new PhotoAlbumCreatedEvent(institutionId);
		evt.setInstitutionFound(false);
		return evt;
	}
}
