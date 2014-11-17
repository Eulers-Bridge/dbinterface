/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import java.util.ArrayList;
import java.util.Collection;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class PhotosReadEvent extends ReadEvent
{
	private Long ownerId;
	private boolean ownerFound=true;
	private boolean photosFound=true;
	private Long totalPhotos;
	private Integer totalPages;
	
	private Collection<PhotoDetails> photos;

	public PhotosReadEvent()
	{
		super();
	}
	
	public PhotosReadEvent(Long ownerId,Collection<PhotoDetails>photos)
	{
		this.ownerId=ownerId;
		this.photos=photos;
	}
	
	public PhotosReadEvent(Long ownerId,
			ArrayList<PhotoDetails> dets, long totalElements,
			int totalPages) 
	{
		this(ownerId,dets);
		this.totalPhotos=totalElements;
		this.totalPages=totalPages;
	}

	/**
	 * @return the ownerId
	 */
	public Long getInstId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setInstId(Long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the totalPhotos
	 */
	public Long getTotalPhotos() {
		return totalPhotos;
	}

	/**
	 * @param totalArticles the totalPhotos to set
	 */
	public void setTotalPhotos(Long totalPhotos) {
		this.totalPhotos = totalPhotos;
	}

	/**
	 * @return the ownerFound
	 */
	public boolean isOwnerFound()
	{
		return ownerFound;
	}

	/**
	 * @param ownerFound the ownerFound to set
	 */
	public void setOwnerFound(boolean ownerFound)
	{
		this.ownerFound = ownerFound;
	}

	/**
	 * @return the photosFound
	 */
	public boolean isPhotosFound()
	{
		return photosFound;
	}

	/**
	 * @param photosFound the photosFound to set
	 */
	public void setPhotosFound(boolean photosFound)
	{
		this.photosFound = photosFound;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the photos
	 */
	public Collection<PhotoDetails> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setArticles(Collection<PhotoDetails> articles) {
		this.photos = articles;
	}

	public static PhotosReadEvent ownerNotFound() 
	{
		PhotosReadEvent nare=new PhotosReadEvent();
		nare.setOwnerFound(false);
		nare.setPhotosFound(false);
		nare.entityFound=false;
		return nare;
	}

}
