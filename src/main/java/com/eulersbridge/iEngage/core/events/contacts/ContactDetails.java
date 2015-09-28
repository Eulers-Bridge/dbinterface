/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contacts;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class ContactDetails extends Details
{
	private Long contactorId;
	private Long contacteeId;
	private Long timestamp;

	public ContactDetails(Long nodeId, Long contactorId, Long contacteeId, Long timestamp)
	{
		super(nodeId);
		this.contactorId=contactorId;
		this.contacteeId=contacteeId;
		this.timestamp=timestamp;
	}

	/**
	 * @return the contactorId
	 */
	public Long getContactorId()
	{
		return contactorId;
	}

	/**
	 * @param contactorId the contactorId to set
	 */
	public void setContactorId(Long contactorId)
	{
		this.contactorId = contactorId;
	}

	/**
	 * @return the contacteeId
	 */
	public Long getContacteeId()
	{
		return contacteeId;
	}

	/**
	 * @param contacteeId the contacteeId to set
	 */
	public void setContacteeId(Long contacteeId)
	{
		this.contacteeId = contacteeId;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp()
	{
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp)
	{
		this.timestamp = timestamp;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ContactDetails [contactorId=" + contactorId + ", contacteeId="
				+ contacteeId + ", timestamp=" + timestamp + ", nodeId="
				+ nodeId + "]";
	}

}
