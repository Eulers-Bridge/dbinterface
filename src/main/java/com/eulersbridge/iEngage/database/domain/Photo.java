/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;

/**
 * @author Greg Newitt
 *
 */
@NodeEntity
public class Photo 
{
	@GraphId Long nodeId;
	String url;
	String title;
	String description;
	Long date;

    private static Logger LOG = LoggerFactory.getLogger(Photo.class);

	/**
	 * @param uRL
	 * @param title
	 * @param description
	 * @param date
	 */
	public Photo(String url, String title, String description, Long date)
	{
		super();
		this.url = url;
		this.title = title;
		this.description = description;
		this.date = date;
	}

	public Photo()
	{
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the uRL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String url) {
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Photo [nodeId=" + nodeId + ", URL=" + url + ", title=" + title
				+ ", description=" + description + ", date=" + date + "]";
	}
	
    public PhotoDetails toPhotoDetails()
    {
        if (LOG.isTraceEnabled()) LOG.trace("toPhotoDetails()");

        PhotoDetails photoDetails = new PhotoDetails(nodeId,url,title,description,date);
        if (LOG.isTraceEnabled()) LOG.trace("photoDetails; "+ photoDetails);
        return photoDetails;
    }

    public static Photo fromPhotoDetails(PhotoDetails photoDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromElectionDetails()");
        Photo photo = new Photo(photoDetails.getUrl(),photoDetails.getTitle(),photoDetails.getDescription(),photoDetails.getDate());
        if (photoDetails.getNodeId()!=null)
        	photo.setNodeId(photoDetails.getNodeId());
        if (LOG.isTraceEnabled()) LOG.trace("photoDetails "+photoDetails);

        if (LOG.isTraceEnabled()) LOG.trace("photo "+photo);
        return photo;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (this.nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;
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
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
		}
		return true;
	}


}