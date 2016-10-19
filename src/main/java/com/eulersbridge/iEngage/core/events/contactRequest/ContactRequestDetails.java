/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.users.UserDetails;

/**
 * @author Greg Newitt
 *
 */
public class ContactRequestDetails extends Details
{
	private String contactDetails;
	private Long requestDate;
	private Long responseDate;
	private Boolean accepted;
	private Boolean rejected;
	private Long userId;
	private UserDetails requesterDetails;
    private UserDetails requestReceiverDetails;

	/**
	 * @param contactDetails
	 * @param userId
	 */
	public ContactRequestDetails(String contactDetails, Long userId)
	{
		super();
		this.contactDetails = contactDetails;
		this.userId = userId;
	}
	/**
	 * @param nodeId
	 * @param contactDetails
	 * @param requestDate
	 * @param responseDate
	 * @param accepted
	 * @param rejected
	 * @param userId
	 */
	public ContactRequestDetails(Long nodeId, String contactDetails,
			Long requestDate, Long responseDate, Boolean accepted,
			Boolean rejected, Long userId, UserDetails requesterDetails)
	{
		super(nodeId);
		this.contactDetails = contactDetails;
		this.requestDate = requestDate;
		this.responseDate = responseDate;
		this.accepted = accepted;
		this.rejected = rejected;
		this.userId = userId;
		this.requesterDetails = requesterDetails;
	}

	public ContactRequestDetails()
	{
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

	public UserDetails getRequesterDetails() {
		return requesterDetails;
	}

	public void setRequesterDetails(UserDetails requesterDetails) {
		this.requesterDetails = requesterDetails;
	}

    public UserDetails getRequestReceiverDetails() {
        return requestReceiverDetails;
    }

    public void setRequestReceiverDetails(UserDetails requestReceiverDetails) {
        this.requestReceiverDetails = requestReceiverDetails;
    }

    @Override
	public String toString()
	{
		return "ContactRequestDetails [nodeId=" + nodeId + ", contactDetails="
				+ contactDetails + ", requestDate=" + requestDate
				+ ", responseDate=" + responseDate + ", accepted=" + accepted
				+ ", rejected=" + rejected + ", userId=" + userId + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (this.nodeId != null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result
					+ ((accepted == null) ? 0 : accepted.hashCode());
			result = prime * result
					+ ((contactDetails == null) ? 0 : contactDetails.hashCode());
			result = prime * result
					+ ((rejected == null) ? 0 : rejected.hashCode());
			result = prime * result
					+ ((requestDate == null) ? 0 : requestDate.hashCode());
			result = prime * result
					+ ((responseDate == null) ? 0 : responseDate.hashCode());
			result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		ContactRequestDetails other = (ContactRequestDetails) obj;

		if (nodeId != null)
		{
			if (nodeId.equals(other.getNodeId()))
				return true;
			else return false;
		}
		else
		{
			if (other.getNodeId() != null) return false;
			
			if (accepted == null)
			{
				if (other.getAccepted() != null) return false;
			}
			else if (!accepted.equals(other.getAccepted())) return false;
			if (contactDetails == null)
			{
				if (other.getContactDetails() != null) return false;
			}
			else if (!contactDetails.equals(other.getContactDetails())) return false;
			if (rejected == null)
			{
				if (other.getRejected() != null) return false;
			}
			else if (!rejected.equals(other.getRejected())) return false;
			if (requestDate == null)
			{
				if (other.getRequestDate() != null) return false;
			}
			else if (!requestDate.equals(other.getRequestDate())) return false;
			if (responseDate == null)
			{
				if (other.getResponseDate() != null) return false;
			}
			else if (!responseDate.equals(other.getResponseDate())) return false;
			if (userId == null)
			{
				if (other.getUserId() != null) return false;
			}
			else if (!userId.equals(other.getUserId())) return false;
		}
		return true;
	}

}
