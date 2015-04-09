/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class PhotoDetails extends Details
{
	String url;
	String thumbNailUrl;
	String title;
	String description;
	Integer sequence;
	Long date;
	Long ownerId;
	private boolean inappropriateContent;
	
	/**
	 * @param nodeId
	 * @param url
	 * @param thumbNailUrl
	 * @param title
	 * @param description
	 * @param date
	 * @param sequence 
	 * @param ownerId
	 */
	public PhotoDetails(Long nodeId, String url, String thumbNailUrl, String title,
			String description, Long date, Integer sequence, Long ownerId, boolean inappropriateContent)
	{
		super(nodeId);
		this.url = url;
		this.thumbNailUrl = thumbNailUrl;
		this.title = title;
		this.description = description;
		this.date = date;
		this.ownerId = ownerId;
		this.sequence = sequence;
		this.inappropriateContent = inappropriateContent;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
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
			result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			result = prime * result + ((thumbNailUrl == null) ? 0 : thumbNailUrl.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhotoDetails other = (PhotoDetails) obj;
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
			if (sequence == null) {
				if (other.sequence != null)
					return false;
			} else if (!sequence.equals(other.sequence))
				return false;
			if (ownerId == null) {
				if (other.ownerId != null)
					return false;
			} else if (!ownerId.equals(other.ownerId))
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
			if (thumbNailUrl == null) {
				if (other.thumbNailUrl != null)
					return false;
			} else if (!thumbNailUrl.equals(other.thumbNailUrl))
				return false;
			
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PhotoDetails [nodeId=" + nodeId + ", url=" + url +", thumbNailUrl=" + thumbNailUrl +", sequence=" + sequence + ", title="
				+ title + ", description=" + description + ", date=" + date
				+ ", ownerId=" + ownerId + "]";
	}

}
