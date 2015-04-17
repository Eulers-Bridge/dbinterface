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

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.events.RequestReadEventEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.photo.CreatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.DeletePhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoCreatedEvent;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotosReadEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotoEvent;
import com.eulersbridge.iEngage.core.events.photo.ReadPhotosEvent;
import com.eulersbridge.iEngage.core.events.photo.UpdatePhotoEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.CreatePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.DeletePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumCreatedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumUpdatedEvent;
import com.eulersbridge.iEngage.core.events.photoAlbums.UpdatePhotoAlbumEvent;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.PhotoService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.domain.FindsParent;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
import com.eulersbridge.iEngage.rest.domain.Photo;
import com.eulersbridge.iEngage.rest.domain.PhotoAlbum;
import com.eulersbridge.iEngage.rest.domain.Photos;
import com.eulersbridge.iEngage.rest.domain.User;

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
	@Autowired
	LikesService likesService;

	public PhotoController()
	{
		if (LOG.isDebugEnabled()) LOG.debug("PhotoController()");
	}

	// Get
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.PHOTO_LABEL
			+ "/{photoId}")
	public @ResponseBody ResponseEntity<Photo> findPhoto(
			@PathVariable Long photoId)
	{
		if (LOG.isInfoEnabled())
			LOG.info(photoId + " attempting to get photo. ");
		ReadPhotoEvent readPhotoEvent = new ReadPhotoEvent(photoId);
		ReadEvent photoReadEvent = photoService.readPhoto(readPhotoEvent);
		if (photoReadEvent.isEntityFound())
		{
			Photo photo = Photo.fromPhotoDetails((PhotoDetails) photoReadEvent
					.getDetails());
			return new ResponseEntity<Photo>(photo, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
		}
	}

	// Get
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.PHOTO_ALBUM_LABEL
			+ "/{photoAlbumId}")
	public @ResponseBody ResponseEntity<PhotoAlbum> findPhotoAlbum(
			@PathVariable Long photoAlbumId)
	{
		if (LOG.isInfoEnabled())
			LOG.info(photoAlbumId + " attempting to get photo. ");
		RequestReadEvent readPhotoAlbumEvent = new RequestReadEvent(
				photoAlbumId);
		ReadEvent photoAlbumReadEvent = photoService
				.readPhotoAlbum(readPhotoAlbumEvent);
		if (photoAlbumReadEvent.isEntityFound())
		{
			PhotoAlbum photoAlbum = PhotoAlbum
					.fromPhotoAlbumDetails((PhotoAlbumDetails) photoAlbumReadEvent
							.getDetails());
			return new ResponseEntity<PhotoAlbum>(photoAlbum, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<PhotoAlbum>(HttpStatus.NOT_FOUND);
		}
	}

	// Create
	@RequestMapping(method = RequestMethod.POST, value = ControllerConstants.PHOTO_LABEL)
	public @ResponseBody ResponseEntity<Photo> createPhoto(
			@RequestBody Photo photo)
	{
		if (LOG.isInfoEnabled())
			LOG.info("attempting to create photo " + photo);
		ResponseEntity<Photo> response;
		if (null==photo)
		{
			response = new ResponseEntity<Photo>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			PhotoCreatedEvent photoCreatedEvent = photoService
					.createPhoto(new CreatePhotoEvent(photo.toPhotoDetails()));
			if (LOG.isDebugEnabled()) LOG.debug("photoCreatedEvent "+photoCreatedEvent);
			if ((null == photoCreatedEvent)
					|| (null == photoCreatedEvent.getNodeId()))
			{
				response = new ResponseEntity<Photo>(HttpStatus.BAD_REQUEST);
			}
			else if (!(photoCreatedEvent.isOwnerFound()))
			{
				response = new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
			}
			else
			{
				Photo result = Photo
						.fromPhotoDetails((PhotoDetails) photoCreatedEvent
								.getDetails());
				if (LOG.isDebugEnabled()) LOG.debug("photo " + result.toString());
				response = new ResponseEntity<Photo>(result, HttpStatus.CREATED);
			}
		}
		return response;
	}

	// Create
	@RequestMapping(method = RequestMethod.POST, value = ControllerConstants.PHOTO_ALBUM_LABEL)
	public @ResponseBody ResponseEntity<PhotoAlbum> createPhotoAlbum(
			@RequestBody PhotoAlbum photoAlbum)
	{
		if (LOG.isInfoEnabled())
			LOG.info("attempting to create photoAlbum " + photoAlbum);
		ResponseEntity<PhotoAlbum> response;
		if (null==photoAlbum)
		{
			response = new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			PhotoAlbumCreatedEvent photoAlbumCreatedEvent = photoService
					.createPhotoAlbum(new CreatePhotoAlbumEvent(photoAlbum
							.toPhotoAlbumDetails()));
			if (null == photoAlbumCreatedEvent)
			{
				response = new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
			}
			else if (!(photoAlbumCreatedEvent.isOwnerFound()))
			{
				response = new ResponseEntity<PhotoAlbum>(HttpStatus.NOT_FOUND);
			}
			else if (!(photoAlbumCreatedEvent.isCreatorFound()))
			{
				response = new ResponseEntity<PhotoAlbum>(HttpStatus.NOT_FOUND);
			}
			else if ((null == photoAlbumCreatedEvent.getDetails())
					|| (null == photoAlbumCreatedEvent.getDetails().getNodeId()))
			{
				response = new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
			}
			else
			{
				PhotoAlbum result = PhotoAlbum
						.fromPhotoAlbumDetails((PhotoAlbumDetails) photoAlbumCreatedEvent
								.getDetails());
				if (LOG.isDebugEnabled())
					LOG.debug("photoAlbum " + result.toString());
				response = new ResponseEntity<PhotoAlbum>(result,
						HttpStatus.CREATED);
			}
		}
		return response;
	}

	// Delete
	@RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.PHOTO_LABEL
			+ "/{photoId}")
	public @ResponseBody ResponseEntity<Boolean> deletePhoto(
			@PathVariable Long photoId)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to delete photo. " + photoId);
		ResponseEntity<Boolean> response;
		DeletedEvent elecEvent = photoService.deletePhoto(new DeletePhotoEvent(
				photoId));
		if (elecEvent.isDeletionCompleted())
			response = new ResponseEntity<Boolean>(
					elecEvent.isDeletionCompleted(), HttpStatus.OK);
		else if (elecEvent.isEntityFound())
			response = new ResponseEntity<Boolean>(
					elecEvent.isDeletionCompleted(), HttpStatus.GONE);
		else response = new ResponseEntity<Boolean>(
				elecEvent.isDeletionCompleted(), HttpStatus.NOT_FOUND);
		return response;
	}

	// Delete
	@RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.PHOTO_ALBUM_LABEL
			+ "/{photoAlbumId}")
	public @ResponseBody ResponseEntity<Boolean> deletePhotoAlbum(
			@PathVariable Long photoAlbumId)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to delete photo. " + photoAlbumId);
		ResponseEntity<Boolean> response;
		DeletedEvent elecEvent = photoService.deletePhotoAlbum(new DeletePhotoAlbumEvent(
				photoAlbumId));
		if (elecEvent.isDeletionCompleted())
			response = new ResponseEntity<Boolean>(
					elecEvent.isDeletionCompleted(), HttpStatus.OK);
		else if (elecEvent.isEntityFound())
			response = new ResponseEntity<Boolean>(
					elecEvent.isDeletionCompleted(), HttpStatus.GONE);
		else response = new ResponseEntity<Boolean>(
				elecEvent.isDeletionCompleted(), HttpStatus.NOT_FOUND);
		return response;
	}

	// Update
	@RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.PHOTO_LABEL
			+ "/{photoId}")
	public @ResponseBody ResponseEntity<Photo> updatePhoto(
			@PathVariable Long photoId, @RequestBody Photo photo)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to update photo. " + photoId);
		UpdatedEvent photoUpdatedEvent = photoService
				.updatePhoto(new UpdatePhotoEvent(photoId, photo
						.toPhotoDetails()));
		if ((null != photoUpdatedEvent))
		{
			if (LOG.isDebugEnabled())
				LOG.debug("photoUpdatedEvent - " + photoUpdatedEvent);
			if (photoUpdatedEvent.isEntityFound())
			{
				Photo restPhoto = Photo.fromPhotoDetails((PhotoDetails) photoUpdatedEvent
						.getDetails());
				if (LOG.isDebugEnabled())
					LOG.debug("restPhoto = " + restPhoto);
				return new ResponseEntity<Photo>(restPhoto, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<Photo>(HttpStatus.BAD_REQUEST);
		}
	}

	// Update
	@RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.PHOTO_ALBUM_LABEL
			+ "/{photoAlbumId}")
	public @ResponseBody ResponseEntity<PhotoAlbum> updatePhotoAlbum(
			@PathVariable Long photoAlbumId, @RequestBody PhotoAlbum photoAlbum)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to update photo. " + photoAlbumId);
		ResponseEntity<PhotoAlbum> response;
		
		UpdatedEvent photoAlbumUpdatedEvent = photoService
				.updatePhotoAlbum(new UpdatePhotoAlbumEvent(photoAlbumId, photoAlbum
						.toPhotoAlbumDetails()));
		if ((null == photoAlbumUpdatedEvent))
		{
			response = new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
		}
		else if (!((PhotoAlbumUpdatedEvent)photoAlbumUpdatedEvent).isOwnerFound())
		{
			response = new ResponseEntity<PhotoAlbum>(HttpStatus.NOT_FOUND);
		}
		else if (!((PhotoAlbumUpdatedEvent)photoAlbumUpdatedEvent).isCreatorFound())
		{
			response = new ResponseEntity<PhotoAlbum>(HttpStatus.NOT_FOUND);
		}
		else if ((null == photoAlbumUpdatedEvent.getDetails())
				|| (null == photoAlbumUpdatedEvent.getDetails().getNodeId()))
		{
			response = new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
		}
		else
		{
			if (LOG.isDebugEnabled())
				LOG.debug("photoUpdatedEvent - " + photoAlbumUpdatedEvent);
			if (photoAlbumUpdatedEvent.isEntityFound())
			{
				PhotoAlbum restPhotoAlbum = PhotoAlbum.fromPhotoAlbumDetails((PhotoAlbumDetails) photoAlbumUpdatedEvent
						.getDetails());
				if (LOG.isDebugEnabled())
					LOG.debug("restPhotoAlbum = " + restPhotoAlbum);
				response = new ResponseEntity<PhotoAlbum>(restPhotoAlbum, HttpStatus.OK);
			}
			else
			{
				response = new ResponseEntity<PhotoAlbum>(HttpStatus.NOT_FOUND);
			}
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to read photos from the database. The
	 * request must be a GET with the ownerId presented as the final portion of
	 * the URL.
	 * <p/>
	 * This method will return the photos read from the database.
	 * 
	 * @param ownerId
	 *            the ownerId of the photo objects to be read.
	 * @return the photos.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.PHOTOS_LABEL
			+ "/{ownerId}")
	public @ResponseBody ResponseEntity<Photos> findPhotos(
			@PathVariable(value = "") Long ownerId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve photos for owner " + ownerId + '.');

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		return getThePhotos(ownerId, sortDirection, pageNumber, pageLength);
	}

	/**
	 * Is passed all the necessary data to delete photos from the database. The
	 * request must be a DELETE with the ownerId presented as the final portion of
	 * the URL.
	 * <p/>
	 * This method will return the photos read from the database.
	 * 
	 * @param ownerId
	 *            the ownerId of the photo objects to be deleted.
	 * @return the photos.
	 * 
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.PHOTOS_LABEL
			+ "/{ownerId}")
	public @ResponseBody ResponseEntity<Long> deleteItems(
			@PathVariable(value = "") Long ownerId)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to delete photos for owner " + ownerId + '.');

		ResponseEntity<Long> response;

		PhotosReadEvent photoEvent = photoService.deletePhotos(
				new ReadPhotosEvent(ownerId));

		if (!photoEvent.isEntityFound())
		{
			response = new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Long numPhotos=photoEvent.getTotalPhotos();
			if (LOG.isDebugEnabled()) LOG.debug("Total photos = "+numPhotos);
			response = new ResponseEntity<Long>(numPhotos, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to read photos from the database. The
	 * request must be a GET with the ownerId presented as the final portion of
	 * the URL.
	 * <p/>
	 * This method will return the photos read from the database.
	 * 
	 * @param ownerId
	 *            the ownerId of the photo objects to be read.
	 * @return the photos.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL
			+ ControllerConstants.PHOTOS_LABEL + "/{userEmail}")
	public @ResponseBody ResponseEntity<Photos> findProfilePhotos(
			@PathVariable(value = "") String userEmail,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve profile photos for user "
					+ userEmail + '.');

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;

		Long ownerId = userService.findUserId(userEmail);

		return getThePhotos(ownerId, sortDirection, pageNumber, pageLength);

	}

	private @ResponseBody ResponseEntity<Photos> getThePhotos(Long ownerId,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		ResponseEntity<Photos> response;

		PhotosReadEvent photoEvent = photoService.findPhotos(
				new ReadPhotosEvent(ownerId), sortDirection, pageNumber,
				pageLength);

		if (!photoEvent.isEntityFound())
		{
			response = new ResponseEntity<Photos>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Photo> photoIter = Photo.toPhotosIterator(photoEvent
					.getPhotos().iterator());
			Photos photos = Photos.fromPhotosIterator(photoIter,
					photoEvent.getTotalPhotos(), photoEvent.getTotalPages());
			response = new ResponseEntity<Photos>(photos, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to read photoAlbums from the database. The
	 * request must be a GET with the ownerId presented as the final portion of
	 * the URL.
	 * <p/>
	 * This method will return the photoAlbums read from the database.
	 * 
	 * @param ownerId
	 *            the ownerId of the photoAlbum objects to be read.
	 * @return the photoAlbums.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.PHOTO_ALBUMS_LABEL
			+ "/{ownerId}")
	public @ResponseBody ResponseEntity<FindsParent> findPhotoAlbums(
			@PathVariable(value = "") Long ownerId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve photoAlbums for owner " + ownerId + '.');

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		ResponseEntity<FindsParent> response;

		AllReadEvent photoAlbumEvent = photoService.findPhotoAlbums(
				new ReadAllEvent(ownerId), sortDirection, pageNumber,
				pageLength);

		if (!photoAlbumEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<PhotoAlbum> photoAlbumIter = PhotoAlbum.toPhotoAlbumsIterator(photoAlbumEvent.getDetails().iterator());
			FindsParent photoAlbums = FindsParent.fromArticlesIterator(photoAlbumIter,
					photoAlbumEvent.getTotalItems(), photoAlbumEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(photoAlbums, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to unlike an event from the database.
	 * The request must be a PUT with the event id presented along with the
	 * userid as the final portion of the URL.
	 * <p/>
	 * This method will return the a boolean result.
	 * 
	 * @param email
	 *            the eventId eventId of the event object to be unliked.
	 * @param email
	 *            the email address of the user unliking the event.
	 * @return the success or failure.
	 * 
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.PHOTO_LABEL
			+ "/{photoId}/likedBy/{email}/")
	public @ResponseBody ResponseEntity<Boolean> unlikeEvent(
			@PathVariable Long photoId, @PathVariable String email)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to have " + email + " unlike photo. "
					+ photoId);
		LikedEvent event = likesService.unlike(new LikeEvent(photoId,
				email));

		ResponseEntity<Boolean> response;

		if (!event.isEntityFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.GONE);
		}
		else if (!event.isUserFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Boolean restEvent = event.isResultSuccess();
			response = new ResponseEntity<Boolean>(restEvent, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to like an event from the database. The
	 * request must be a PUT with the event id presented along with the userid
	 * as the final portion of the URL.
	 * <p/>
	 * This method will return the a boolean result.
	 * 
	 * @param email
	 *            the eventId eventId of the event object to be liked.
	 * @param email
	 *            the email address of the user liking the event.
	 * @return the success or failure.
	 * 
	 */
	@RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.PHOTO_LABEL
			+ "/{eventId}/likedBy/{email}/")
	public @ResponseBody ResponseEntity<Boolean> likeEvent(
			@PathVariable Long photoId, @PathVariable String email)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to have " + email + " like news article. "
					+ photoId);
		LikedEvent event = likesService
				.like(new LikeEvent(photoId, email));

		ResponseEntity<Boolean> response;

		if (!event.isEntityFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.GONE);
		}
		else if (!event.isUserFound())
		{
			response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Boolean restEvent = event.isResultSuccess();
			response = new ResponseEntity<Boolean>(restEvent, HttpStatus.OK);
		}
		return response;
	}

	// likes
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.PHOTO_LABEL
			+ "/{photoId}" + ControllerConstants.LIKES_LABEL)
	public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
			@PathVariable Long photoId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve liked users from photo " + photoId
					+ '.');
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(
				new LikesLikeableObjectEvent(photoId), sortDirection,
				pageNumber, pageLength);
		Iterator<LikeInfo> likes = User
				.toLikesIterator(likeableObjectLikesEvent.getUserDetails()
						.iterator());
		if (likes.hasNext() == false)
		{
			ReadEvent readPollEvent = photoService
					.readPhoto(new ReadPhotoEvent(photoId));
			if (!readPollEvent.isEntityFound())
				return new ResponseEntity<Iterator<LikeInfo>>(
						HttpStatus.NOT_FOUND);
			else return new ResponseEntity<Iterator<LikeInfo>>(likes,
					HttpStatus.OK);
		}
		else return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
	}

}
