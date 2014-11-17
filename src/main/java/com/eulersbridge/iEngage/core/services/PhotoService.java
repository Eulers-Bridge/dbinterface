/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotosReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotosEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;

/**
 * @author Greg Newitt
 *
 */
public interface PhotoService
{
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public PhotoReadEvent readPhoto(ReadPhotoEvent readPhotoEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public PhotoUpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public PhotoDeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent);
	public PhotosReadEvent findPhotos(ReadPhotosEvent findPhotoEvent,Direction dir,int pageNumber,int pageLength);
}
