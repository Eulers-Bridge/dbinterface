/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotosReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotosEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.CreatePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.DeletePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.UpdatePhotoAlbumEvent;
import com.eulersbridge.iEngage.security.SecurityConstants;

/**
 * @author Greg Newitt
 *
 */
public interface PhotoService
{
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public PhotoAlbumCreatedEvent createPhotoAlbum(CreatePhotoAlbumEvent createPhotoAlbumEvent);
	@PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public ReadEvent readPhoto(ReadPhotoEvent readPhotoEvent);
	@PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public ReadEvent readPhotoAlbum(RequestReadEvent readPhotoAlbumEvent);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public UpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public UpdatedEvent updatePhotoAlbum(UpdatePhotoAlbumEvent updatePhotoAlbumEvent);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public DeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public DeletedEvent deletePhotoAlbum(DeletePhotoAlbumEvent deletePhotoAlbumEvent);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public PhotosReadEvent findPhotos(ReadPhotosEvent findPhotoEvent,Direction dir,int pageNumber,int pageLength);
	@PreAuthorize("hasAnyRole('"+SecurityConstants.USER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
	public AllReadEvent findPhotoAlbums(ReadAllEvent readAllEvent,Direction dir,int pageNumber,int pageLength);
	
	public PhotosReadEvent deletePhotos(ReadPhotosEvent any);
}
