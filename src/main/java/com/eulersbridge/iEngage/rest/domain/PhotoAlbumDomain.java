/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails;
import com.eulersbridge.iEngage.rest.controller.PhotoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Greg Newitt
 *
 */
public class PhotoAlbumDomain extends ResourceSupport
{
    private Long nodeId;
    private String name;
    private String description;
    private String location;
    private String thumbNailUrl;
    private Long created;
    private Long creatorId;
    private Long ownerId;
    private Long modified;

    private static Logger LOG = LoggerFactory.getLogger(PhotoAlbumDomain.class);

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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
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
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
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
	 * @return the creatorId
	 */
	public Long getCreatorId()
	{
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(Long creatorId)
	{
		this.creatorId = creatorId;
	}

	/**
	 * @return the created
	 */
	public Long getCreated()
	{
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Long created)
	{
		this.created = created;
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
	 * @return the modified
	 */
	public Long getModified()
	{
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Long modified)
	{
		this.modified = modified;
	}

	public PhotoAlbumDetails toPhotoAlbumDetails()
	{
    	PhotoAlbumDetails photoAlbumDetails = new PhotoAlbumDetails(getNodeId(), getName(), getLocation(), getDescription(), getThumbNailUrl(), getCreatorId(), getCreated(), getOwnerId(),getModified());
        if (LOG.isTraceEnabled()) LOG.trace("photoAlbumDetails "+photoAlbumDetails);
        return photoAlbumDetails;
	}

	public static PhotoAlbumDomain fromPhotoAlbumDetails(PhotoAlbumDetails details)
	{
    	PhotoAlbumDomain photoAlbum = new PhotoAlbumDomain();
        String simpleName= PhotoAlbumDomain.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
        photoAlbum.setNodeId(details.getNodeId());
        photoAlbum.setName(details.getName());
        photoAlbum.setLocation(details.getLocation());
        photoAlbum.setThumbNailUrl(details.getThumbNailUrl());
        photoAlbum.setDescription(details.getDescription());
        photoAlbum.setCreatorId(details.getCreatorId());
        photoAlbum.setCreated(details.getCreated());
        photoAlbum.setModified(details.getModified());
        photoAlbum.setOwnerId(details.getOwnerId());

	    // {!begin selfRel}
        photoAlbum.add(linkTo(PhotoController.class).slash(name).slash(photoAlbum.nodeId).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        photoAlbum.add(linkTo(PhotoController.class).slash(name).slash(photoAlbum.nodeId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        photoAlbum.add(linkTo(PhotoController.class).slash(name).slash(photoAlbum.nodeId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin readAll}
	    photoAlbum.add(linkTo(PhotoController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

        return photoAlbum;
	}
	
	public static Iterator<PhotoAlbumDomain> toPhotoAlbumsIterator(Iterator<? extends Details> iter)
	{
		if (null==iter) return null;
		ArrayList <PhotoAlbumDomain> photoAlbums=new ArrayList<PhotoAlbumDomain>();
		while(iter.hasNext())
		{
			PhotoAlbumDetails dets=(PhotoAlbumDetails)iter.next();
			PhotoAlbumDomain thisPhotoAlbum= PhotoAlbumDomain.fromPhotoAlbumDetails(dets);
			Link self = thisPhotoAlbum.getLink("self");
			thisPhotoAlbum.removeLinks();
			thisPhotoAlbum.add(self);
			photoAlbums.add(thisPhotoAlbum);		
		}
		return photoAlbums.iterator();
	}
}
