package com.eulersbridge.iEngage.core.events.photoAlbums;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 */

public class PhotoAlbumDetails extends Details
{
	private String name;
	private String location;
	private String description;
	private String thumbNailUrl;
	private Long created;
	private Long creatorId;
	private Long ownerId;
	private Long modified;

	public PhotoAlbumDetails()
	{
		super();
	}

	/**
	 * @param nodeId
	 * @param name
	 * @param location
	 * @param description
	 * @param created
	 * @param ownerId
	 * @param modified
	 */
	public PhotoAlbumDetails(Long nodeId, String name, String location, String description, String thumbNailUrl,
			Long creatorId, Long created, Long ownerId, Long modified)
	{
		super(nodeId);
		this.name = name;
		this.location = location;
		this.description = description;
		this.thumbNailUrl = thumbNailUrl;
		this.created = created;
		this.ownerId = ownerId;
		this.creatorId = creatorId;
		this.modified = modified;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (nodeId != null)
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
			result = prime * result
					+ ((thumbNailUrl == null) ? 0 : thumbNailUrl.hashCode());
			result = prime * result
					+ ((modified == null) ? 0 : modified.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
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
		PhotoAlbumDetails other = (PhotoAlbumDetails) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null) return false;
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
			if (location == null)
			{
				if (other.location != null) return false;
			}
			else if (!location.equals(other.location)) return false;
			if (thumbNailUrl == null)
			{
				if (other.thumbNailUrl != null) return false;
			}
			else if (!thumbNailUrl.equals(other.thumbNailUrl)) return false;
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
			if (ownerId == null)
			{
				if (other.ownerId != null) return false;
			}
			else if (!ownerId.equals(other.ownerId)) return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PhotoAlbumDetails [nodeId=" + nodeId + ", name=" + name
				+ ", location=" + location + ", description=" + description + ", thumbNailUrl=" + thumbNailUrl
				+ ", created=" + created + ", ownerId=" + ownerId
				+ ", modified=" + modified + "]";
	}


}
