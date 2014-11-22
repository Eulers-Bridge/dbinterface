/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import com.eulersbridge.iEngage.core.events.photoAlbums.PhotoAlbumDetails;

/**
 * @author Greg Newitt
 *
 */
@NodeEntity
public class PhotoAlbum
{
    @GraphId private Long nodeId;
    private String name;
    private String description;
    private String location;
    private Long created;
	@RelatedTo(type = DatabaseDomainConstants.HAS_PHOTO_ALBUM_LABEL, direction=Direction.BOTH) @Fetch
    private Owner owner;
    private Long modified;
	@RelatedToVia(direction=Direction.BOTH, type=DatabaseDomainConstants.LIKES_LABEL)
	private Set<Like> likes;

    private static Logger LOG = LoggerFactory.getLogger(PhotoAlbum.class);

    /**
	 * @param name
	 * @param description
	 * @param created
	 * @param owner
	 * @param modified
	 */
	public PhotoAlbum(String name, String description, String location, Long created, Owner owner, Long modified)
	{
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.created = created;
		this.owner = owner;
		this.modified = modified;
	}

	public PhotoAlbum()
    {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

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
	 * @return the owner
	 */
	public Owner getOwner()
	{
		return owner;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setOwner(Owner owner)
	{
		this.owner = owner;
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

	/**
	 * @return the likes
	 */
	public Set<Like> getLikes()
	{
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Set<Like> likes)
	{
		this.likes = likes;
	}

	public static PhotoAlbum fromPhotoAlbumDetails(PhotoAlbumDetails photoAlbumDetails)
    {
        if (LOG.isTraceEnabled()) LOG.trace("fromPhotoAlbumDetails()");
        PhotoAlbum photoAlbum = new PhotoAlbum();
        if (LOG.isTraceEnabled()) LOG.trace("photoAlbumDetails "+photoAlbumDetails);
        photoAlbum.setNodeId(photoAlbumDetails.getNodeId());
        photoAlbum.setName(photoAlbumDetails.getName());
        photoAlbum.setDescription(photoAlbumDetails.getDescription());
        photoAlbum.setLocation(photoAlbumDetails.getLocation());
        photoAlbum.setCreated(photoAlbumDetails.getCreated());
        photoAlbum.setModified(photoAlbumDetails.getModified());
        Owner thisOwner=new Owner();
        thisOwner.setNodeId(photoAlbumDetails.getOwnerId());
        photoAlbum.setOwner(thisOwner);

        if (LOG.isTraceEnabled()) LOG.trace("photoAlbum "+photoAlbum);
        return photoAlbum;
    }

    public PhotoAlbumDetails toPhotoAlbumDetails()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toPhotoAlbumDetails()");
        PhotoAlbumDetails photoAlbumDetails = new PhotoAlbumDetails();
        if (LOG.isTraceEnabled()) LOG.trace("photoAlbum "+this);
        photoAlbumDetails.setNodeId(this.getNodeId());
        photoAlbumDetails.setName(getName());
        photoAlbumDetails.setDescription(getDescription());
        photoAlbumDetails.setLocation(getLocation());
        photoAlbumDetails.setCreated(getCreated());
        photoAlbumDetails.setOwnerId(getOwner().getNodeId());
        photoAlbumDetails.setModified(getModified());

        if (LOG.isTraceEnabled()) LOG.trace("photoAlbumDetails; "+ photoAlbumDetails);
        return photoAlbumDetails;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (null!=nodeId)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result
				+ ((modified == null) ? 0 : modified.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
	}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PhotoAlbum other = (PhotoAlbum) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null)
				return false;
			if (created == null)
			{
				if (other.created != null) return false;
			}
			else if (!created.equals(other.created)) return false;
			if (description == null)
			{
				if (other.description != null) return false;
			}
			else if (!description.equals(other.description)) return false;
			if (likes == null)
			{
				if (other.likes != null) return false;
			}
			else if (!likes.equals(other.likes)) return false;
			if (modified == null)
			{
				if (other.modified != null) return false;
			}
			else if (!modified.equals(other.modified)) return false;
			if (name == null)
			{
				if (other.name != null) return false;
			}
			else if (!name.equals(other.name)) return false;
			if (location == null)
			{
				if (other.location != null) return false;
			}
			else if (!location.equals(other.location)) return false;
			if (owner == null)
			{
				if (other.owner != null) return false;
			}
			else if (!owner.equals(other.owner)) return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PhotoAlbum [nodeId=" + nodeId + ", name=" + name
				+ ", description=" + description + ", location=" + location
				+ ", created=" + created+ ", owner=" + owner + ", modified="
				+ modified + ", likes="+ likes + "]";
	}

}
