/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDeletedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoReadEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.repository.PhotoRepository;

/**
 * @author Greg Newitt
 *
 */
public class PhotoEventHandler implements PhotoService
{
    private static Logger LOG = LoggerFactory.getLogger(PhotoEventHandler.class);

    private PhotoRepository photoRepository;


	/**
	 * @param photoRepository
	 */
	public PhotoEventHandler(PhotoRepository photoRepository)
	{
		super();
		this.photoRepository = photoRepository;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#createNewsArticle(com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent)
	 */
	@Override
	public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent)
	{
        PhotoDetails photoDetails = createPhotoEvent.getPhotoDetails();
        Photo photo = Photo.fromPhotoDetails(photoDetails);
        
//		if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+photo.getOwnerId());
//    	Object owner=photoRepository.findOne(photo.getOwnerId());
    	PhotoCreatedEvent photoCreatedEvent;
//    	if (owner!=null)
    	{
//    		photo.setOwner(owner);
    		Photo result = photoRepository.save(photo);
        	photoCreatedEvent = new PhotoCreatedEvent(result.getNodeId(), result.toPhotoDetails());
    	}
//    	else
    	{
//    		photoCreatedEvent=PhotoCreatedEvent.ownerNotFound(photoDetails.getOwnerId());
    	}
        return photoCreatedEvent;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#requestReadPhoto(com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent)
	 */
	@Override
	public PhotoReadEvent readPhoto(ReadPhotoEvent readPhotoEvent)
	{
        Photo photo = photoRepository.findOne(readPhotoEvent.getNodeId());
        PhotoReadEvent photoReadEvent;
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
	public PhotoUpdatedEvent updatePhoto(UpdatePhotoEvent updatePhotoEvent)
	{
        PhotoDetails photoDetails = updatePhotoEvent.getPhotoDetails();
        Photo photo = Photo.fromPhotoDetails(photoDetails);
        Long photoId = photoDetails.getNodeId();
        PhotoUpdatedEvent resultEvt;
        
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
	public PhotoDeletedEvent deletePhoto(DeletePhotoEvent deletePhotoEvent)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePhotoEvent= "+deletePhotoEvent);
        Long photoId = deletePhotoEvent.getNodeId();
        PhotoDeletedEvent photoDeletedEvent;
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

}
