/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;

/**
 * @author Greg Newitt
 *
 */
public class ContactRequest extends ResourceSupport
{
	private Long nodeId;
	private String contactDetails;
	private Long requestDate;
	private Long responseDate;
	private Boolean accepted;
	private Boolean rejected;
	private Long userId;
	
    private static Logger LOG = LoggerFactory.getLogger(ContactRequest.class);

    public ContactRequest()
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static ContactRequest fromContactRequestDetails(ContactRequestDetails contactRequestDetails)
    {
        ContactRequest contactRequest = new ContactRequest();
        String simpleName = ContactRequest.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        contactRequest.setNodeId(contactRequestDetails.getNodeId());
        contactRequest.setRequestDate(contactRequestDetails.getRequestDate());
        contactRequest.setContactDetails(contactRequestDetails.getContactDetails());
        contactRequest.setResponseDate(contactRequestDetails.getResponseDate());
        contactRequest.setAccepted(contactRequestDetails.getAccepted());
        contactRequest.setRejected(contactRequestDetails.getRejected());
        contactRequest.setUserId(contactRequestDetails.getUserId());

/*        // {!begin selfRel}
        badge.add(linkTo(ContactRequestController.class).slash(name)
                .slash(badge.badgeId).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        badge.add(linkTo(ContactRequestController.class).slash(name + 's')
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}
*/
        return contactRequest;
    }

    public ContactRequestDetails toContactRequestDetails(){
        ContactRequestDetails badgeDetails = new ContactRequestDetails(nodeId, contactDetails, requestDate, responseDate, accepted, rejected, userId);
        return badgeDetails;
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
	 * @return the contactDetails
	 */
	public String getContactDetails()
	{
		return contactDetails;
	}
	/**
	 * @param contactDetails the contactDetails to set
	 */
	public void setContactDetails(String contactDetails)
	{
		this.contactDetails = contactDetails;
	}
	/**
	 * @return the requestDate
	 */
	public Long getRequestDate()
	{
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Long requestDate)
	{
		this.requestDate = requestDate;
	}
	/**
	 * @return the responseDate
	 */
	public Long getResponseDate()
	{
		return responseDate;
	}
	/**
	 * @param responseDate the responseDate to set
	 */
	public void setResponseDate(Long responseDate)
	{
		this.responseDate = responseDate;
	}
	/**
	 * @return the accepted
	 */
	public Boolean getAccepted()
	{
		return accepted;
	}
	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(Boolean accepted)
	{
		this.accepted = accepted;
	}
	/**
	 * @return the rejected
	 */
	public Boolean getRejected()
	{
		return rejected;
	}
	/**
	 * @param rejected the rejected to set
	 */
	public void setRejected(Boolean rejected)
	{
		this.rejected = rejected;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ContactRequest [nodeId=" + nodeId + ", contactDetails="
				+ contactDetails + ", requestDate=" + requestDate
				+ ", responseDate=" + responseDate + ", accepted=" + accepted
				+ ", rejected=" + rejected + ", userId=" + userId + "]";
	}


}
