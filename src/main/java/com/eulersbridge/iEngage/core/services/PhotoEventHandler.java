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
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumsReadEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.ReadPhotoAlbumsEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.UpdatePhotoAlbumEvent;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Photo;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
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
    private OwnerRepository ownerRepository;


	/**
	 * @param photoRepository
	 */
	public PhotoEventHandler(PhotoRepository photoRepository, PhotoAlbumRepository photoAlbumRepository,OwnerRepository ownerRepository)
	{
		super();
		this.photoRepository = photoRepository;
		this.photoAlbumRepository = photoAlbumRepository;
		this.ownerRepository = ownerRepository;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.PhotoService#createNewsArticle(com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent)
	 */
	@Override
	public PhotoCreatedEvent createPhoto(CreatePhotoEvent createPhotoEvent)
	{
        PhotoDetails photoDetails = (PhotoDetails) createPhotoEvent.getDetails();
        Photo photo = Photo.fromPhotoDetails(photoDetails);
        
        Long ownerId=photoDetails.getOwnerId();
    	if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+ownerId);
		Owner owner=null;
		if (ownerId!=null) owner=ownerRepository.findOne(ownerId);
		
    	PhotoCreatedEvent photoCreatedEvent;
    	if (null==owner)
    	{
    		photoCreatedEvent=PhotoCreatedEvent.ownerNotFound(photoDetails.getOwnerId());
    	}
    	else
    	{
    		photo.setOwner(owner);
    		Photo result = photoRepository.save(photo);
        	photoCreatedEvent = new PhotoCreatedEvent(result.getNodeId(), result.toPhotoDetails());
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
        Long ownerId = findPhotoEvent.getParentId();
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
	public PhotosReadEvent deletePhotos(ReadPhotosEvent deletePhotosEvent)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePhotos deletePhotosEvent = "+deletePhotosEvent);
        Long ownerId = deletePhotosEvent.getParentId();
		Page <Photo>photos=null;
		ArrayList<PhotoDetails> dets=new ArrayList<PhotoDetails>();
        
		Pageable pageable=new PageRequest(0,100,Direction.DESC,"p.date");
		PhotosReadEvent result=null;
		
		if (LOG.isDebugEnabled()) LOG.debug("OwnerId "+ownerId);
		photos=photoRepository.deletePhotosByOwnerId(ownerId,pageable);

		result=new PhotosReadEvent(ownerId,dets,photos.getTotalElements(),photos.getTotalPages());
		
		return result;
	}

	@Override
	public PhotoAlbumCreatedEvent createPhotoAlbum(
			CreatePhotoAlbumEvent createPhotoAlbumEvent)
	{
	       PhotoAlbumDetails photoAlbumDetails = (PhotoAlbumDetails) createPhotoAlbumEvent.getDetails();
	        PhotoAlbum photoAlbum = PhotoAlbum.fromPhotoAlbumDetails(photoAlbumDetails);
	        
	        Long ownerId=photoAlbumDetails.getOwnerId();
			if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+ownerId);
			Owner owner=null;
			if (ownerId!=null) owner=ownerRepository.findOne(ownerId);
			
	    	PhotoAlbumCreatedEvent photoAlbumCreatedEvent;
	    	if (null==owner)
	    	{
	    		photoAlbumCreatedEvent=PhotoAlbumCreatedEvent.ownerNotFound(photoAlbumDetails.getOwnerId());
	    	}
	    	else
	    	{
	    		photoAlbum.setOwner(owner);

		        Long creatorId=photoAlbumDetails.getCreatorId();
				if (LOG.isDebugEnabled()) LOG.debug("Finding owner with creatorId = "+creatorId);
				Owner creator=null;
				if (creatorId!=null) creator=ownerRepository.findOne(creatorId);
		    	if (null==creator)
		    	{
		    		photoAlbumCreatedEvent=PhotoAlbumCreatedEvent.creatorNotFound(photoAlbumDetails.getCreatorId());
		    	}
		    	else
		    	{
		    		photoAlbum.setCreator(creator);
		    		PhotoAlbum result = photoAlbumRepository.save(photoAlbum);
		        	photoAlbumCreatedEvent = new PhotoAlbumCreatedEvent(result.toPhotoAlbumDetails());
		    	}
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
	public UpdatedEvent updatePhotoAlbum(
			UpdatePhotoAlbumEvent updatePhotoAlbumEvent)
	{
        PhotoAlbumDetails photoAlbumDetails = (PhotoAlbumDetails) updatePhotoAlbumEvent.getDetails();
        PhotoAlbum photoAlbum = PhotoAlbum.fromPhotoAlbumDetails(photoAlbumDetails);
        Long photoAlbumId = photoAlbumDetails.getNodeId();
        UpdatedEvent resultEvt;
        
        if(LOG.isDebugEnabled()) LOG.debug("photoAlbum Id is " + photoAlbumId);
        PhotoAlbum photoAlbumOld = photoAlbumRepository.findOne(photoAlbumId);
        if(photoAlbumOld ==null)
        {
            if(LOG.isDebugEnabled()) LOG.debug("photo album entity not found " + photoAlbumId);
            resultEvt = PhotoAlbumUpdatedEvent.notFound(photoAlbumId);
        }
        else
        {
            PhotoAlbum result = photoAlbumRepository.save(photoAlbum);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            resultEvt = new PhotoAlbumUpdatedEvent(result.getNodeId(), result.toPhotoAlbumDetails());
        }
        return resultEvt;
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

	@Override
	public PhotoAlbumsReadEvent findPhotoAlbums(
			ReadPhotoAlbumsEvent findPhotoAlbumsEvent, Direction dir,
			int pageNumber, int pageLength)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered findPhotoAlbums findPhotoAlbumsEvent = "+findPhotoAlbumsEvent);
        Long ownerId = findPhotoAlbumsEvent.getParentId();
		Page <PhotoAlbum>photoAlbums=null;
		ArrayList<PhotoAlbumDetails> dets=new ArrayList<PhotoAlbumDetails>();
        
		PhotoAlbumsReadEvent result=null;
		
		if (LOG.isDebugEnabled()) LOG.debug("OwnerId "+ownerId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,dir,"p.date");
		photoAlbums=photoAlbumRepository.findByOwnerId(ownerId, pageable);

		if (photoAlbums!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+photoAlbums.getTotalElements()+" total pages ="+photoAlbums.getTotalPages());
			Iterator<PhotoAlbum> iter=photoAlbums.iterator();
			while (iter.hasNext())
			{
				PhotoAlbum na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getName());
				PhotoAlbumDetails det=na.toPhotoAlbumDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found ownerId.
				PhotoAlbum inst=photoAlbumRepository.findOne(ownerId);
				if ( (null==inst) || (null==inst.getNodeId()) )
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ownerId)");
					result=PhotoAlbumsReadEvent.institutionNotFound();
				}
				else
				{	
					result=new PhotoAlbumsReadEvent(ownerId,dets,photoAlbums.getTotalElements(),photoAlbums.getTotalPages());
				}
			}
			else
			{	
				result=new PhotoAlbumsReadEvent(ownerId,dets,photoAlbums.getTotalElements(),photoAlbums.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
			result=PhotoAlbumsReadEvent.institutionNotFound();
		}
		return result;
	}

}
