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

/**
 * @author Greg Newitt
 *
 */
public interface PhotoService
{
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public PhotoAlbumCreatedEvent createPhotoAlbum(CreatePhotoAlbumEvent createPhotoAlbumEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public ReadEvent readPhoto(ReadPhotoEvent readPhotoEvent);
	@PreAuthorize("hasRole('ROLE_USER')")
	public ReadEvent readPhotoAlbum(RequestReadEvent readPhotoAlbumEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public UpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public UpdatedEvent updatePhotoAlbum(UpdatePhotoAlbumEvent updatePhotoAlbumEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public DeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent);
	@PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
	public DeletedEvent deletePhotoAlbum(DeletePhotoAlbumEvent deletePhotoAlbumEvent);
	public PhotosReadEvent findPhotos(ReadPhotosEvent findPhotoEvent,Direction dir,int pageNumber,int pageLength);
	public AllReadEvent findPhotoAlbums(ReadAllEvent readAllEvent,Direction dir,int pageNumber,int pageLength);
	
	public PhotosReadEvent deletePhotos(ReadPhotosEvent any);
}
