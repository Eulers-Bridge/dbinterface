/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.rest.controller.PhotoController;

/**
 * @author Greg Newitt
 *
 */
public class Photo extends ResourceSupport
{
	Long nodeId;
	String url;
	String thumbNailUrl;
	String title;
	String description;
	Long date;
	Long ownerId;
	Integer sequence;
	private boolean inappropriateContent=false;
	private Long  numOfLikes;

    private static Logger LOG = LoggerFactory.getLogger(Photo.class);

    /**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the thumbNailUrl
	 */
	public String getThumbNailUrl()
	{
		return thumbNailUrl;
	}

	/**
	 * @param thumbNailUrl the thumbNailUrl to set
	 */
	public void setThumbNailUrl(String thumbNailUrl)
	{
		this.thumbNailUrl = thumbNailUrl;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return the sequence
	 */
	public Integer getSequence()
	{
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence)
	{
		this.sequence = sequence;
	}

	/**
	 * @return the inappropriateContent
	 */
	public boolean isInappropriateContent()
	{
		return inappropriateContent;
	}

	/**
	 * @param inappropriateContent the inappropriateContent to set
	 */
	public void setInappropriateContent(boolean inappropriateContent)
	{
		this.inappropriateContent = inappropriateContent;
	}

	/**
	 * @return the numOfLikes
	 */
	public Long getNumOfLikes()
	{
		return numOfLikes;
	}

	/**
	 * @param numOfLikes the numOfLikes to set
	 */
	public void setNumOfLikes(Long numOfLikes)
	{
		this.numOfLikes = numOfLikes;
	}

	public static Photo fromPhotoDetails(PhotoDetails photoDetails)
    {
    	Photo photo = new Photo();
        String simpleName=Photo.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
        photo.setNodeId(photoDetails.getNodeId());
        photo.setTitle(photoDetails.getTitle());
        photo.setDescription(photoDetails.getDescription());
        photo.setDate(photoDetails.getDate());
        photo.setUrl(photoDetails.getUrl());
        photo.setThumbNailUrl(photoDetails.getThumbNailUrl());
        photo.setOwnerId(photoDetails.getOwnerId());
        photo.setSequence(photoDetails.getSequence());
        photo.setInappropriateContent(photoDetails.isInappropriateContent());
        photo.setNumOfLikes(photoDetails.getNumOfLikes());

	    // {!begin selfRel}
        photo.add(linkTo(PhotoController.class).slash(name).slash(photo.nodeId).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        photo.add(linkTo(PhotoController.class).slash(name).slash(photo.nodeId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        photo.add(linkTo(PhotoController.class).slash(name).slash(photo.nodeId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin readAll}
	    photo.add(linkTo(PhotoController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

        return photo;
    }

    public PhotoDetails toPhotoDetails()
    {
    	PhotoDetails photoDetails = new PhotoDetails(getNodeId(), getUrl(), getThumbNailUrl(), getTitle(), getDescription(), getDate(), getSequence(), getOwnerId(),isInappropriateContent());
        if (LOG.isTraceEnabled()) LOG.trace("photoDetails "+photoDetails);
        return photoDetails;
    }

	public static Iterator<Photo> toPhotosIterator( Iterator<PhotoDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <Photo> photos=new ArrayList<Photo>();
		while(iter.hasNext())
		{
			PhotoDetails dets=iter.next();
			Photo thisPhoto=Photo.fromPhotoDetails(dets);
			Link self = thisPhoto.getLink("self");
			thisPhoto.removeLinks();
			thisPhoto.add(self);
			photos.add(thisPhoto);		
		}
		return photos.iterator();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Photo [nodeId=" + nodeId + ", url=" + url + ", thumbNailUrl=" + thumbNailUrl + ", title=" + title
				+ ", description=" + description + ", date=" + date
				+ ", ownerId=" + ownerId + "]";
	}
}
