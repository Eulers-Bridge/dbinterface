/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotosReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotosEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.CreatePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.DeletePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDeletedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumReadEvent;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.repository.PhotoAlbumRepository;
import com.eulersbridge.iEngage.database.repository.PhotoRepository;

/**
 * @author Greg Newitt
 *
 */
public class PhotoEventHandler implements PhotoService
{
    private static Logger LOG = LoggerFactory.getLogger(PhotoEventHandler.class);

    private PhotoRepository photoRepository;
    private PhotoAlbumRepository photoAlbumRepository;


	/**
	 * @param photoRepository
	 */
	public PhotoEventHandler(PhotoRepository photoRepository, PhotoAlbumRepository photoAlbumRepository)
	{
		super();
		this.photoRepository = photoRepository;
		this.photoAlbumRepository = photoAlbumRepository;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#createNewsArticle(com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent)
	 */
	@Override
	public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent)
	{
        PhotoDetails photoDetails = (PhotoDetails) createPhotoEvent.getDetails();
        Photo photo = Photo.fromPhotoDetails(photoDetails);
        
		if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+photoDetails.getOwnerId());
    	Photo ownerPhoto=photoRepository.findOne(photoDetails.getOwnerId());
    	PhotoCreatedEvent photoCreatedEvent;
    	if (ownerPhoto!=null)
    	{
        	Owner owner=new Owner();
        	owner.setNodeId(ownerPhoto.getNodeId());
    		photo.setOwner(owner);
    		Photo result = photoRepository.save(photo);
        	photoCreatedEvent = new PhotoCreatedEvent(result.getNodeId(), result.toPhotoDetails());
    	}
    	else
    	{
    		photoCreatedEvent=PhotoCreatedEvent.ownerNotFound(photoDetails.getOwnerId());
    	}
        return photoCreatedEvent;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#requestReadPhoto(com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent)
	 */
	@Override
	public ReadEvent readPhoto(ReadPhotoEvent readPhotoEvent)
	{
        Photo photo = photoRepository.findOne(readPhotoEvent.getNodeId());
        ReadEvent photoReadEvent;
        if (photo!=null)
        {
            photoReadEvent = new PhotoReadEvent(photo.getNodeId(), photo.toPhotoDetails());
        }
        else{
        	photoReadEvent = PhotoReadEvent.notFound(readPhotoEvent.getNodeId());
        }
        return photoReadEvent;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#updatePhoto(com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent)
	 */
	@Override
	public UpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent)
	{
        PhotoDetails photoDetails = (PhotoDetails) updatePhotoEvent.getDetails();
        Photo photo = Photo.fromPhotoDetails(photoDetails);
        Long photoId = photoDetails.getNodeId();
        UpdatedEvent resultEvt;
        
        if(LOG.isDebugEnabled()) LOG.debug("photo Id is " + photoId);
        Photo photoOld = photoRepository.findOne(photoId);
        if(photoOld ==null)
        {
            if(LOG.isDebugEnabled()) LOG.debug("photo entity not found " + photoId);
            resultEvt = PhotoUpdatedEvent.notFound(photoId);
        }
        else
        {
            Photo result = photoRepository.save(photo);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            resultEvt = new PhotoUpdatedEvent(result.getNodeId(), result.toPhotoDetails());
        }
        return resultEvt;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#deletePhoto(com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent)
	 */
	@Override
	public DeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePhotoEvent= "+deletePhotoEvent);
        Long photoId = deletePhotoEvent.getNodeId();
        DeletedEvent photoDeletedEvent;
        if (LOG.isDebugEnabled()) LOG.debug("deletePhoto("+photoId+")");
        Photo photo = photoRepository.findOne(photoId);
        if (null == photo)
        {
        	photoDeletedEvent = PhotoDeletedEvent.notFound(photoId);
        }
        else
        {
        	photoRepository.delete(photoId);
        	photoDeletedEvent = new PhotoDeletedEvent(photoId);
        }
        return photoDeletedEvent;
	}

	public PhotosReadEvent findPhotos(ReadPhotosEvent findPhotoEvent,Direction dir,int pageNumber,int pageLength)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered findPhotos findPhotoEvent = "+findPhotoEvent);
        Long ownerId = findPhotoEvent.getOwnerId();
		Page <Photo>photos=null;
		ArrayList<PhotoDetails> dets=new ArrayList<PhotoDetails>();
        
		PhotosReadEvent result=null;
		
		if (LOG.isDebugEnabled()) LOG.debug("OwnerId "+ownerId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,dir,"p.date");
		photos=photoRepository.findByOwnerId(ownerId, pageable);

		if (photos!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+photos.getTotalElements()+" total pages ="+photos.getTotalPages());
			Iterator<Photo> iter=photos.iterator();
			while (iter.hasNext())
			{
				Photo na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getTitle());
				PhotoDetails det=na.toPhotoDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found ownerId.
				Photo inst=photoRepository.findOne(ownerId);
				if ( (null==inst) || (null==inst.getNodeId()) )
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ownerId)");
					result=PhotosReadEvent.ownerNotFound();
				}
				else
				{	
					result=new PhotosReadEvent(ownerId,dets,photos.getTotalElements(),photos.getTotalPages());
				}
			}
			else
			{	
				result=new PhotosReadEvent(ownerId,dets,photos.getTotalElements(),photos.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
			result=PhotosReadEvent.ownerNotFound();
		}
		return result;
	}

	@Override
	public PhotoAlbumCreatedEvent createPhotoAlbum(
			CreatePhotoAlbumEvent createPhotoAlbumEvent)
	{
	       PhotoAlbumDetails photoAlbumDetails = (PhotoAlbumDetails) createPhotoAlbumEvent.getDetails();
	        PhotoAlbum photoAlbum = PhotoAlbum.fromPhotoAlbumDetails(photoAlbumDetails);
	        
			if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+photoAlbumDetails.getOwnerId());
	    	Photo ownerPhoto=photoRepository.findOne(photoAlbumDetails.getOwnerId());
	    	PhotoAlbumCreatedEvent photoAlbumCreatedEvent;
	    	if (ownerPhoto!=null)
	    	{
	    		Owner owner=new Owner();
	    		owner.setNodeId(ownerPhoto.getNodeId());
	    		photoAlbum.setOwner(owner);
	    		PhotoAlbum result = photoAlbumRepository.save(photoAlbum);
	        	photoAlbumCreatedEvent = new PhotoAlbumCreatedEvent(result.toPhotoAlbumDetails());
	    	}
	    	else
	    	{
	    		photoAlbumCreatedEvent=PhotoAlbumCreatedEvent.ownerNotFound(photoAlbumDetails.getOwnerId());
	    	}
	        return photoAlbumCreatedEvent;
	}

	@Override
	public ReadEvent readPhotoAlbum(RequestReadEvent readPhotoAlbumEvent)
	{
        PhotoAlbum photoAlbum = photoAlbumRepository.findOne(readPhotoAlbumEvent.getNodeId());
        ReadEvent photoAlbumReadEvent;
        if (photoAlbum!=null)
        {
            photoAlbumReadEvent = new PhotoAlbumReadEvent(photoAlbum.getNodeId(), photoAlbum.toPhotoAlbumDetails());
        }
        else{
        	photoAlbumReadEvent = PhotoAlbumReadEvent.notFound(readPhotoAlbumEvent.getNodeId());
        }
        return photoAlbumReadEvent;
	}

	@Override
	public DeletedEvent deletePhotoAlbum(
			DeletePhotoAlbumEvent deletePhotoAlbumEvent)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePhotoEvent= "+deletePhotoAlbumEvent);
        Long photoAlbumId = deletePhotoAlbumEvent.getNodeId();
        DeletedEvent photoDeletedEvent;
        if (LOG.isDebugEnabled()) LOG.debug("deletePhotoAlbum("+photoAlbumId+")");
        PhotoAlbum photoAlbum = photoAlbumRepository.findOne(photoAlbumId);
        if (null == photoAlbum)
        {
        	photoDeletedEvent = PhotoAlbumDeletedEvent.notFound(photoAlbumId);
        }
        else
        {
        	photoAlbumRepository.delete(photoAlbumId);
        	photoDeletedEvent = new PhotoAlbumDeletedEvent(photoAlbumId);
        }
        return photoDeletedEvent;
	}

}
