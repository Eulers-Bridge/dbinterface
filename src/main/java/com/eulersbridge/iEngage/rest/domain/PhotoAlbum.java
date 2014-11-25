/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails;
import com.eulersbridge.iEngage.rest.controller.PhotoController;

/**
 * @author Greg Newitt
 *
 */
public class PhotoAlbum extends ResourceSupport
{
    private Long nodeId;
    private String name;
    private String description;
    private String location;
    private Long created;
    private Long creatorId;
    private Long ownerId;
    private Long modified;

    private static Logger LOG = LoggerFactory.getLogger(PhotoAlbum.class);

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
    	PhotoAlbumDetails photoAlbumDetails = new PhotoAlbumDetails(getNodeId(), getName(), getLocation(), getDescription(), getCreatorId(), getCreated(), getOwnerId(),getModified());
        if (LOG.isTraceEnabled()) LOG.trace("photoAlbumDetails "+photoAlbumDetails);
        return photoAlbumDetails;
	}

	public static PhotoAlbum fromPhotoAlbumDetails(PhotoAlbumDetails details)
	{
    	PhotoAlbum photoAlbum = new PhotoAlbum();
        String simpleName=PhotoAlbum.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
        photoAlbum.setNodeId(details.getNodeId());
        photoAlbum.setName(details.getName());
        photoAlbum.setLocation(details.getLocation());
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

}
