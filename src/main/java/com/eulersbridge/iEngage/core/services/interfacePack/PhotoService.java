/**
 *
 */
package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.photo.*;
import com.eulersbridge.iEngage.core.events.photoAlbums.CreatePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.DeletePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.UpdatePhotoAlbumEvent;
import com.eulersbridge.iEngage.rest.domain.PhotoDomain;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Greg Newitt
 */
public interface PhotoService {
  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public PhotoAlbumCreatedEvent createPhotoAlbum(CreatePhotoAlbumEvent createPhotoAlbumEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readPhoto(ReadPhotoEvent readPhotoEvent);

  @PreAuthorize("hasRole('" + SecurityConstants.USER_ROLE + "')")
  public ReadEvent readPhotoAlbum(RequestReadEvent readPhotoAlbumEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public UpdatedEvent updatePhotoAlbum(UpdatePhotoAlbumEvent updatePhotoAlbumEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public DeletedEvent deletePhotoAlbum(DeletePhotoAlbumEvent deletePhotoAlbumEvent);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public PhotosReadEvent findPhotos(ReadPhotosEvent findPhotoEvent, Direction dir, int pageNumber, int pageLength);

  @PreAuthorize("hasAnyRole('" + SecurityConstants.USER_ROLE + "','" + SecurityConstants.ADMIN_ROLE + "')")
  public AllReadEvent findPhotoAlbums(ReadAllEvent readAllEvent, Direction dir, int pageNumber, int pageLength);

  public PhotosReadEvent deletePhotos(ReadPhotosEvent any);

  @PreAuthorize("hasRole('" + SecurityConstants.ADMIN_ROLE + "')")
  public RequestHandledEvent<List<PhotoDomain>> findPhotosToInstitution(Long institutionId);
}
