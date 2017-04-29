/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Greg Newitt
 *
 */
public class Contact extends ResourceSupport
{
	private Long nodeId;
	private Long contactorId;
	private Long contacteeId;
	private Long timestamp;
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
	public static Contact fromContactDetails(ContactDetails contactDetails)
	{
		Contact result=new Contact();
		result.setNodeId(contactDetails.getNodeId());
		result.setContacteeId(contactDetails.getContacteeId());
		result.setContactorId(contactDetails.getContactorId());
		result.setTimestamp(contactDetails.getTimestamp());
		return result;
	}

}
