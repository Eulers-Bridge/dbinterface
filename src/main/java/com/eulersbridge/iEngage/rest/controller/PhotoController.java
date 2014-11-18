/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.eulersbridge.iEngage.core.services.PhotoService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.Photo;
import com.eulersbridge.iEngage.rest.domain.Photos;

/**
 * @author Greg Newitt
 *
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class PhotoController
{
    private static Logger LOG = LoggerFactory.getLogger(PhotoController.class);
    
    @Autowired
    PhotoService photoService;
    @Autowired
    UserService userService;

	public PhotoController()
	{
		if (LOG.isDebugEnabled()) LOG.debug("PhotoController()");
	}


    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.PHOTO_LABEL+"/{photoId}")
    public @ResponseBody ResponseEntity<Photo> findPhoto(@PathVariable Long photoId)
    {
        if (LOG.isInfoEnabled()) LOG.info(photoId+" attempting to get photo. ");
        ReadPhotoEvent readPhotoEvent= new ReadPhotoEvent(photoId);
        PhotoReadEvent photoReadEvent= photoService.readPhoto(readPhotoEvent);
        if (photoReadEvent.isEntityFound()){
            Photo photo = Photo.fromPhotoDetails(photoReadEvent.getPhotoDetails());
            return new ResponseEntity<Photo>(photo, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
        }
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.PHOTO_LABEL)
    public @ResponseBody ResponseEntity<Photo> createPhoto(@RequestBody Photo photo)
    {
        if (LOG.isInfoEnabled()) LOG.info("attempting to create photo "+photo);
        PhotoCreatedEvent photoCreatedEvent = photoService.createPhoto(new CreatePhotoEvent(photo.toPhotoDetails()));
        ResponseEntity<Photo> response;
        if((null==photoCreatedEvent)||(null==photoCreatedEvent.getNodeId()))
        {
            response=new ResponseEntity<Photo>(HttpStatus.BAD_REQUEST);
        }
        else if (!(photoCreatedEvent.isOwnerFound()))
        {
            response=new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Photo result = Photo.fromPhotoDetails(photoCreatedEvent.getPhotoDetails());
            if (LOG.isDebugEnabled()) LOG.debug("photo "+result.toString());
            response=new ResponseEntity<Photo>(result, HttpStatus.CREATED);
        }
        return response;
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.PHOTO_LABEL+"/{photoId}")
    public @ResponseBody ResponseEntity<Boolean> deletePhoto(@PathVariable Long photoId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete photo. " + photoId);
		ResponseEntity<Boolean> response;
        PhotoDeletedEvent elecEvent = photoService.deletePhoto(new DeletePhotoEvent(photoId));
		if (elecEvent.isDeletionCompleted())
			response=new ResponseEntity<Boolean>(elecEvent.isDeletionCompleted(),HttpStatus.OK);
		else if (elecEvent.isEntityFound())
			response=new ResponseEntity<Boolean>(elecEvent.isDeletionCompleted(),HttpStatus.GONE);
		else
			response=new ResponseEntity<Boolean>(elecEvent.isDeletionCompleted(),HttpStatus.NOT_FOUND);
		return response;
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.PHOTO_LABEL+"/{photoId}")
    public @ResponseBody ResponseEntity<Photo> updatePhoto(@PathVariable Long photoId, @RequestBody Photo photo)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update photo. " + photoId);
        PhotoUpdatedEvent photoUpdatedEvent= photoService.updatePhoto(new UpdatePhotoEvent(photoId, photo.toPhotoDetails()));
        if ((null!=photoUpdatedEvent))
        {
        	if (LOG.isDebugEnabled()) LOG.debug("photoUpdatedEvent - "+photoUpdatedEvent);
        	if(photoUpdatedEvent.isEntityFound())
        	{
        		Photo restPhoto = Photo.fromPhotoDetails(photoUpdatedEvent.getPhotoDetails());
        		if (LOG.isDebugEnabled()) LOG.debug("restPhoto = "+restPhoto);
        		return new ResponseEntity<Photo>(restPhoto, HttpStatus.OK);
        	}
            else
            {
                return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<Photo>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Is passed all the necessary data to read photos from the database.
     * The request must be a GET with the ownerId presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the photos read from the database.
     * 
     * @param ownerId the ownerId of the photo objects to be read.
     * @return the photos.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.PHOTOS_LABEL+"/{ownerId}")
	public @ResponseBody ResponseEntity<Photos> findPhotos(@PathVariable(value="") Long ownerId,
			@RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
			@RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize) 
	{
		int pageNumber=0;
		int pageLength=10;
		pageNumber=Integer.parseInt(page);
		pageLength=Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve photos for owner "+ownerId+'.');
		
		Direction sortDirection=Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection=Direction.ASC;
		return getThePhotos(ownerId, sortDirection, pageNumber, pageLength);
	}
    
    /**
     * Is passed all the necessary data to read photos from the database.
     * The request must be a GET with the ownerId presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the photos read from the database.
     * 
     * @param ownerId the ownerId of the photo objects to be read.
     * @return the photos.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+ControllerConstants.PHOTOS_LABEL+"/{userEmail}")
	public @ResponseBody ResponseEntity<Photos> findProfilePhotos(@PathVariable(value="") String userEmail,
			@RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
			@RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize) 
	{
		int pageNumber=0;
		int pageLength=10;
		pageNumber=Integer.parseInt(page);
		pageLength=Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve profile photos for user "+userEmail+'.');
		
		Direction sortDirection=Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection=Direction.ASC;
		
		Long ownerId=userService.findUserId(userEmail);
		
		return getThePhotos(ownerId, sortDirection, pageNumber, pageLength);
		
	}
	
	private @ResponseBody ResponseEntity<Photos> getThePhotos(Long ownerId,Direction sortDirection, int pageNumber, int pageLength)
	{	
		ResponseEntity<Photos> response;
		
		PhotosReadEvent photoEvent=photoService.findPhotos(new ReadPhotosEvent(ownerId),sortDirection, pageNumber,pageLength);
  	
		if (!photoEvent.isEntityFound())
		{
			response = new ResponseEntity<Photos>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Photo> photoIter = Photo.toPhotosIterator(photoEvent.getPhotos().iterator());
			Photos photos = Photos.fromPhotosIterator(photoIter, photoEvent.getTotalPhotos(), photoEvent.getTotalPages());
			response = new ResponseEntity<Photos>(photos,HttpStatus.OK);
		}
		return response;
	}
    
}