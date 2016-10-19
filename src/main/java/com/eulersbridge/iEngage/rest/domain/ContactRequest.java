/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.Details;
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
	private UserProfile requesterProfile;
	private UserProfile requestReceiverProfile;
	
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
		contactRequest.setRequesterProfile(UserProfile.fromUserDetails(contactRequestDetails.getRequesterDetails()));
		if (contactRequestDetails.getRequestReceiverDetails() != null)
			contactRequest.setRequestReceiverProfile(UserProfile.fromUserDetails(contactRequestDetails.getRequestReceiverDetails()));

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
        ContactRequestDetails badgeDetails = new ContactRequestDetails(nodeId, contactDetails,
				requestDate, responseDate, accepted, rejected, userId, null);
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

	public UserProfile getRequesterProfile() {
		return requesterProfile;
	}

	public void setRequesterProfile(UserProfile requesterProfile) {
		this.requesterProfile = requesterProfile;
	}

	public UserProfile getRequestReceiverProfile() {
		return requestReceiverProfile;
	}

	public void setRequestReceiverProfile(UserProfile requestReceiverProfile) {
		this.requestReceiverProfile = requestReceiverProfile;
	}

	@Override
	public String toString()
	{
		return "ContactRequest [nodeId=" + nodeId + ", contactDetails="
				+ contactDetails + ", requestDate=" + requestDate
				+ ", responseDate=" + responseDate + ", accepted=" + accepted
				+ ", rejected=" + rejected + ", userId=" + userId + "]";
	}

	public static Iterator<ContactRequest> toContactRequestsIterator(
			Iterator<? extends Details> iterator)
	{
		if (null==iterator) return null;
		ArrayList <ContactRequest> contactRequests=new ArrayList<ContactRequest>();
		while(iterator.hasNext())
		{
			ContactRequestDetails dets=(ContactRequestDetails)iterator.next();
			ContactRequest thisContactRequest=ContactRequest.fromContactRequestDetails(dets);
			Link self = thisContactRequest.getLink("self");
			thisContactRequest.removeLinks();
			if (self!=null)
				thisContactRequest.add(self);
			contactRequests.add(thisContactRequest);		
		}
		return contactRequests.iterator();
	}
}
