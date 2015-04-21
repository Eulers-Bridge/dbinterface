/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;

/**
 * @author Greg Newitt
 *
 */
@RelationshipEntity(type=DatabaseDomainConstants.CONTACT_LABEL)
public class Contact
{
	@GraphId private Long nodeId;
	@Fetch
	@StartNode private User contactor;
	@EndNode private User contactee;
	private Long timestamp;
	
    private static Logger LOG = LoggerFactory.getLogger(Contact.class);

    public Contact() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public ContactDetails toContactDetails()
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toContactDetails()");
	    	    
	    Long contactorId=null;
		Long contacteeId=null;
		
		if (getContactor()!=null)
			contactorId=getContactor().getNodeId();
		if (getContactee()!=null)
			contacteeId=getContactee().getNodeId();
		ContactDetails details = new ContactDetails(getNodeId(),contactorId, contacteeId, getTimestamp());
	    if (LOG.isTraceEnabled()) LOG.trace("Contact "+this);

	    return details;
	}
	
	static public Contact fromContactDetails(ContactDetails details)
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toContactDetails()");
	    Contact contact=null;
	    User contactor=null;
	    User contactee=null;
	    if (details!=null)
	    {
	    	contact=new Contact();
	    	contact.setNodeId(details.getNodeId());
	    	contact.setTimestamp(details.getTimestamp());
		    contactor=new User(details.getContactorId());
			contactee=new User(details.getContacteeId());
			contact.setContactee(contactee);
			contact.setContactor(contactor);
	    }
	    	
		
	    if (LOG.isTraceEnabled()) LOG.trace("Contact "+contact);

	    return contact;
	}
	
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

	public Long getTimestamp()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getTimestamp() = "+timestamp);
		return timestamp;
	}

	/**
	 * @param date the date to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the contactor
	 */
	public User getContactor() {
		return contactor;
	}

	/**
	 * @param contactor the contactor to set
	 */
	public void setContactor(User contactor) {
		this.contactor = contactor;
	}

	/**
	 * @return the contactee
	 */
	public User getContactee() {
		return contactee;
	}

	/**
	 * @param contactee the contactee to set
	 */
	public void setContactee(User contactee) {
		this.contactee = contactee;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VoteReminder [nodeId=" + nodeId + ", contactor=" + contactor
				+ ", contactee=" + contactee + ", timestamp=" + timestamp
				+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (getNodeId()==null)
		{
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result
				+ ((contactee == null) ? 0 : contactee.hashCode());
		result = prime * result + ((contactor == null) ? 0 : contactor.hashCode());
		}
		else
		{
			result=getNodeId().hashCode();
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
		Contact other = (Contact) obj;
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
			if (timestamp == null) {
				if (other.timestamp != null)
					return false;
			} else if (!timestamp.equals(other.timestamp))
				return false;
			if (contactee == null) {
				if (other.contactee != null)
					return false;
			} else if (!contactee.equals(other.contactee))
				return false;
			if (contactor == null) {
				if (other.contactor != null)
					return false;
			} else if (!contactor.equals(other.contactor))
				return false;
		}
		return true;
	}
}
