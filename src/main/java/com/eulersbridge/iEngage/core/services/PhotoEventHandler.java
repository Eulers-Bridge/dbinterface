/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.photo.*;
import com.eulersbridge.iEngage.core.events.photoAlbums.*;
import com.eulersbridge.iEngage.core.services.interfacePack.PhotoService;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NodeRepository;
import com.eulersbridge.iEngage.database.repository.PhotoAlbumRepository;
import com.eulersbridge.iEngage.database.repository.PhotoRepository;
import com.eulersbridge.iEngage.rest.domain.PhotoDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Greg Newitt
 */

@Service
public class PhotoEventHandler implements PhotoService {
  private static Logger LOG = LoggerFactory.getLogger(PhotoEventHandler.class);

  private final PhotoRepository photoRepository;
  private final PhotoAlbumRepository photoAlbumRepository;
  private final NodeRepository nodeRepository;
  private final InstitutionRepository insRepo;

  @Autowired
  public PhotoEventHandler(PhotoRepository photoRepository, PhotoAlbumRepository photoAlbumRepository, NodeRepository nodeRepository, InstitutionRepository insRepo) {
    super();
    this.photoRepository = photoRepository;
    this.photoAlbumRepository = photoAlbumRepository;
    this.nodeRepository = nodeRepository;
    this.insRepo = insRepo;
  }

  @Override
  public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent) {
    PhotoDetails photoDetails = (PhotoDetails) createPhotoEvent.getDetails();
    Photo photo = Photo.fromPhotoDetails(photoDetails);

    Long ownerId = photoDetails.getOwnerId();
    if (LOG.isDebugEnabled())
      LOG.debug("Finding owner with ownerId = " + ownerId);
    Node owner = null;
    if (ownerId != null) owner = nodeRepository.findOne(ownerId);

    PhotoCreatedEvent photoCreatedEvent;
    if (null == owner) {
      photoCreatedEvent = PhotoCreatedEvent.ownerNotFound(photoDetails.getOwnerId());
    } else {
      photo.setOwner(new Node(owner.getNodeId()));
      Photo result = photoRepository.save(photo);
      photoCreatedEvent = new PhotoCreatedEvent(result.getNodeId(), result.toPhotoDetails());
    }
    return photoCreatedEvent;
  }

  @Override
  public ReadEvent readPhoto(ReadPhotoEvent readPhotoEvent) {
    Photo photo = photoRepository.findOne(readPhotoEvent.getNodeId());
    ReadEvent photoReadEvent;
    if (photo != null) {
      photoReadEvent = new PhotoReadEvent(photo.getNodeId(), photo.toPhotoDetails());
    } else {
      photoReadEvent = PhotoReadEvent.notFound(readPhotoEvent.getNodeId());
    }
    return photoReadEvent;
  }

  @Override
  public UpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent) {
    PhotoDetails photoDetails = (PhotoDetails) updatePhotoEvent.getDetails();
    Photo photo = Photo.fromPhotoDetails(photoDetails);
    Long photoId = photoDetails.getNodeId();
    UpdatedEvent resultEvt;

    if (LOG.isDebugEnabled()) LOG.debug("photo Id is " + photoId);
    Photo photoOld = photoRepository.findOne(photoId);
    if (photoOld == null) {
      if (LOG.isDebugEnabled()) LOG.debug("photo entity not found " + photoId);
      resultEvt = PhotoUpdatedEvent.notFound(photoId);
    } else {
      Photo result = photoRepository.save(photo, 0);
      if (LOG.isDebugEnabled())
        LOG.debug("updated successfully" + result.getNodeId());
      resultEvt = new PhotoUpdatedEvent(result.getNodeId(), result.toPhotoDetails());
    }
    return resultEvt;
  }

  @Override
  public DeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deletePhotoEvent= " + deletePhotoEvent);
    Long photoId = deletePhotoEvent.getNodeId();
    DeletedEvent photoDeletedEvent;
    if (LOG.isDebugEnabled()) LOG.debug("deletePhoto(" + photoId + ")");
    Photo photo = photoRepository.findOne(photoId);
    if (null == photo) {
      photoDeletedEvent = PhotoDeletedEvent.notFound(photoId);
    } else {
      photoRepository.delete(photoId);
      photoDeletedEvent = new PhotoDeletedEvent(photoId);
    }
    return photoDeletedEvent;
  }

  public PhotosReadEvent findPhotos(ReadPhotosEvent findPhotoEvent, Direction dir, int pageNumber, int pageLength) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered findPhotos findPhotoEvent = " + findPhotoEvent);
    Long ownerId = findPhotoEvent.getParentId();
    Page<Photo> photos = null;
    ArrayList<PhotoDetails> dets = new ArrayList<PhotoDetails>();

    PhotosReadEvent result = null;

    if (LOG.isDebugEnabled()) LOG.debug("OwnerId " + ownerId);
    Pageable pageable = new PageRequest(pageNumber, pageLength, dir, "p.date");
    photos = photoRepository.findByOwnerId(ownerId, pageable);

    if (photos != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + photos.getTotalElements() + " total pages =" + photos.getTotalPages());
      Iterator<Photo> iter = photos.iterator();
      while (iter.hasNext()) {
        Photo na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getTitle());
        PhotoDetails det = na.toPhotoDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found ownerId.
        Photo inst = photoRepository.findOne(ownerId);
        if ((null == inst) || (null == inst.getNodeId())) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ownerId)");
          result = PhotosReadEvent.ownerNotFound();
        } else {
          result = new PhotosReadEvent(ownerId, dets, photos.getTotalElements(), photos.getTotalPages());
        }
      } else {
        result = new PhotosReadEvent(ownerId, dets, photos.getTotalElements(), photos.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
      result = PhotosReadEvent.ownerNotFound();
    }
    return result;
  }

  @Override
  public PhotosReadEvent deletePhotos(ReadPhotosEvent deletePhotosEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deletePhotos deletePhotosEvent = " + deletePhotosEvent);
    Long ownerId = deletePhotosEvent.getParentId();
    ArrayList<PhotoDetails> dets = new ArrayList<PhotoDetails>();

    PhotosReadEvent result = null;

    if (LOG.isDebugEnabled()) LOG.debug("OwnerId " + ownerId);
    Long numPhotos = photoRepository.deletePhotosByOwnerId(ownerId);

    result = new PhotosReadEvent(ownerId, dets, numPhotos, numPhotos.intValue());

    return result;
  }

  @Override
  public PhotoAlbumCreatedEvent createPhotoAlbum(
    CreatePhotoAlbumEvent createPhotoAlbumEvent) {
    PhotoAlbumDetails photoAlbumDetails = (PhotoAlbumDetails) createPhotoAlbumEvent.getDetails();
    PhotoAlbum photoAlbum = PhotoAlbum.fromPhotoAlbumDetails(photoAlbumDetails);

    Long ownerId = photoAlbumDetails.getOwnerId();
    if (LOG.isDebugEnabled())
      LOG.debug("Finding owner with ownerId = " + ownerId);
    Node owner = null;
    if (ownerId != null) owner = nodeRepository.findOne(ownerId);

    PhotoAlbumCreatedEvent photoAlbumCreatedEvent;
    if (null == owner) {
      photoAlbumCreatedEvent = PhotoAlbumCreatedEvent.ownerNotFound(photoAlbumDetails.getOwnerId());
    } else {
      photoAlbum.setOwner(new Node(owner.getNodeId()));

      Long creatorId = photoAlbumDetails.getCreatorId();
      if (LOG.isDebugEnabled())
        LOG.debug("Finding owner with creatorId = " + creatorId);
      Node creator = null;
      if (creatorId != null) creator = nodeRepository.findOne(creatorId);
      if (null == creator) {
        photoAlbumCreatedEvent = PhotoAlbumCreatedEvent.creatorNotFound(photoAlbumDetails.getCreatorId());
      } else {
        photoAlbum.setCreator(new Node(creator.getNodeId()));
        PhotoAlbum result = photoAlbumRepository.save(photoAlbum);
        photoAlbumCreatedEvent = new PhotoAlbumCreatedEvent(result.toPhotoAlbumDetails());
      }
    }
    return photoAlbumCreatedEvent;
  }

  @Override
  public ReadEvent readPhotoAlbum(RequestReadEvent readPhotoAlbumEvent) {
    PhotoAlbum photoAlbum = photoAlbumRepository.findOne(readPhotoAlbumEvent.getNodeId());
    ReadEvent photoAlbumReadEvent;
    if (photoAlbum != null) {
      photoAlbumReadEvent = new PhotoAlbumReadEvent(photoAlbum.getNodeId(), photoAlbum.toPhotoAlbumDetails());
    } else {
      photoAlbumReadEvent = PhotoAlbumReadEvent.notFound(readPhotoAlbumEvent.getNodeId());
    }
    return photoAlbumReadEvent;
  }

  @Override
  public UpdatedEvent updatePhotoAlbum(
    UpdatePhotoAlbumEvent updatePhotoAlbumEvent) {
    PhotoAlbumDetails photoAlbumDetails = (PhotoAlbumDetails) updatePhotoAlbumEvent.getDetails();
    PhotoAlbum photoAlbum = PhotoAlbum.fromPhotoAlbumDetails(photoAlbumDetails);
    Long photoAlbumId = photoAlbumDetails.getNodeId();
    UpdatedEvent resultEvt;

    if (LOG.isDebugEnabled()) LOG.debug("photoAlbum Id is " + photoAlbumId);
    PhotoAlbum photoAlbumOld = photoAlbumRepository.findOne(photoAlbumId);
    if (photoAlbumOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("photo album entity not found " + photoAlbumId);
      resultEvt = PhotoAlbumUpdatedEvent.notFound(photoAlbumId);
    } else {
      PhotoAlbum result = photoAlbumRepository.save(photoAlbum, 0);
      if (LOG.isDebugEnabled())
        LOG.debug("updated successfully" + result.getNodeId());
      resultEvt = new PhotoAlbumUpdatedEvent(result.getNodeId(), result.toPhotoAlbumDetails());
    }
    return resultEvt;
  }

  @Override
  public DeletedEvent deletePhotoAlbum(
    DeletePhotoAlbumEvent deletePhotoAlbumEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deletePhotoEvent= " + deletePhotoAlbumEvent);
    Long photoAlbumId = deletePhotoAlbumEvent.getNodeId();
    DeletedEvent photoDeletedEvent;
    if (LOG.isDebugEnabled())
      LOG.debug("deletePhotoAlbum(" + photoAlbumId + ")");
    PhotoAlbum photoAlbum = photoAlbumRepository.findOne(photoAlbumId);
    if (null == photoAlbum) {
      photoDeletedEvent = PhotoAlbumDeletedEvent.notFound(photoAlbumId);
    } else {
      photoAlbumRepository.delete(photoAlbumId);
      photoDeletedEvent = new PhotoAlbumDeletedEvent(photoAlbumId);
    }
    return photoDeletedEvent;
  }

  @Override
  public AllReadEvent findPhotoAlbums(
    ReadAllEvent findPhotoAlbumsEvent, Direction dir,
    int pageNumber, int pageLength) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered findPhotoAlbums findPhotoAlbumsEvent = " + findPhotoAlbumsEvent);
    Long ownerId = findPhotoAlbumsEvent.getParentId();
    Page<PhotoAlbum> photoAlbums = null;
    ArrayList<PhotoAlbumDetails> dets = new ArrayList<PhotoAlbumDetails>();

    AllReadEvent result = null;

    if (LOG.isDebugEnabled()) LOG.debug("OwnerId " + ownerId);
    Pageable pageable = new PageRequest(pageNumber, pageLength, dir, "p.created");
    photoAlbums = photoAlbumRepository.findByOwnerId(ownerId, pageable);

    if (photoAlbums != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + photoAlbums.getTotalElements() + " total pages =" + photoAlbums.getTotalPages());
      Iterator<PhotoAlbum> iter = photoAlbums.iterator();
      while (iter.hasNext()) {
        PhotoAlbum na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getName());
        PhotoAlbumDetails det = na.toPhotoAlbumDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found ownerId.
        Node owner = nodeRepository.findOne(ownerId);
        if ((null == owner) || (null == owner.getNodeId())) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ownerId)");
          result = AllReadEvent.notFound(null);
        } else {
          result = new AllReadEvent(ownerId, dets, photoAlbums.getTotalElements(), photoAlbums.getTotalPages());
        }
      } else {
        result = new AllReadEvent(ownerId, dets, photoAlbums.getTotalElements(), photoAlbums.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
      result = AllReadEvent.notFound(null);
    }
    return result;
  }

  @Override
  public RequestHandledEvent<List<PhotoDomain>> findPhotosToInstitution(Long institutionId) {
    Institution ins = insRepo.findOne(institutionId, 0);
    if (ins == null)
      return RequestHandledEvent.targetNotFound();
    List<Photo> photos = photoRepository.findPhotosByInstitution(institutionId);
    assert (photos != null);
    List<PhotoDomain> photoDomains = photos.stream().map(photo -> {
      return PhotoDomain.fromPhotoDetails(photo.toPhotoDetails());
    }).collect(Collectors.toList());
    return new RequestHandledEvent<>(photoDomains);
  }
}
