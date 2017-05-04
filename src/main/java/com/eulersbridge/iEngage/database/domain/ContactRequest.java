package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author Greg Newitt
 */
@NodeEntity
public class ContactRequest extends Node {

  private String contactDetails;
  private Long requestDate;
  private Long responseDate;
  private Boolean accepted;
  private Boolean rejected;
  @Relationship(type = DatabaseDomainConstants.CONTACT_REQUEST_LABEL, direction = Relationship.INCOMING)
//    @Fetch
  private Node user;

  private static Logger LOG = LoggerFactory.getLogger(ContactRequest.class);

  public ContactRequestDetails toContactRequestDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toContactRequestDetails()");

    Long userId = null;
    UserDetails requesterDetails = null;
    if (getUser() != null) {
      userId = getUser().getNodeId();
      requesterDetails = getUser().toUserDetails();
    }
    ContactRequestDetails details = new ContactRequestDetails(getNodeId(), getContactDetails(),
      getRequestDate(), getResponseDate(), getAccepted(), getRejected(), userId, requesterDetails);
    if (LOG.isTraceEnabled()) LOG.trace("contactRequest " + this);

    BeanUtils.copyProperties(this, details);
    return details;
  }

  public static ContactRequest fromContactRequestDetails(ContactRequestDetails dets) {
    if (LOG.isTraceEnabled()) LOG.trace("fromContactRequestDetails()");

    ContactRequest contactRequest = new ContactRequest();
    contactRequest.setNodeId(dets.getNodeId());
    contactRequest.setContactDetails(dets.getContactDetails());
    contactRequest.setRequestDate(dets.getRequestDate());
    contactRequest.setResponseDate(dets.getResponseDate());
    User user = new User();
    user.setNodeId(dets.getUserId());
    contactRequest.setUser(user.toNode());
    contactRequest.setAccepted(dets.getAccepted());
    contactRequest.setRejected(dets.getRejected());

    if (LOG.isTraceEnabled())
      LOG.trace("contactRequest " + contactRequest + " contactRequestDetails " + dets);
    return contactRequest;
  }

  /**
   * @return the contactDetails
   */
  public String getContactDetails() {
    return contactDetails;
  }

  /**
   * @param contactDetails the contactDetails to set
   */
  public void setContactDetails(String contactDetails) {
    this.contactDetails = contactDetails;
  }

  /**
   * @return the requestDate
   */
  public Long getRequestDate() {
    return requestDate;
  }

  /**
   * @param requestDate the requestDate to set
   */
  public void setRequestDate(Long requestDate) {
    this.requestDate = requestDate;
  }

  /**
   * @return the responseDate
   */
  public Long getResponseDate() {
    return responseDate;
  }

  /**
   * @param responseDate the responseDate to set
   */
  public void setResponseDate(Long responseDate) {
    this.responseDate = responseDate;
  }

  /**
   * @return the accepted
   */
  public Boolean getAccepted() {
    return accepted;
  }

  /**
   * @param accepted the accepted to set
   */
  public void setAccepted(Boolean accepted) {
    this.accepted = accepted;
  }

  /**
   * @return the rejected
   */
  public Boolean getRejected() {
    return rejected;
  }

  /**
   * @param rejected the rejected to set
   */
  public void setRejected(Boolean rejected) {
    this.rejected = rejected;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return (User) user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(Node user) {
    this.user = user;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ContactRequest [nodeId=" + nodeId + ", contactDetails="
      + contactDetails + ", requestDate=" + requestDate
      + ", responseDate=" + responseDate + ", accepted=" + accepted
      + ", rejected=" + rejected + ", user=" + user + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (this.nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
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
      result = prime * result + ((user == null) ? 0 : user.hashCode());
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ContactRequest other = (ContactRequest) obj;

    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null) return false;

      if (accepted == null) {
        if (other.accepted != null) return false;
      } else if (!accepted.equals(other.accepted)) return false;
      if (contactDetails == null) {
        if (other.contactDetails != null) return false;
      } else if (!contactDetails.equals(other.contactDetails)) return false;
      if (rejected == null) {
        if (other.rejected != null) return false;
      } else if (!rejected.equals(other.rejected)) return false;
      if (requestDate == null) {
        if (other.requestDate != null) return false;
      } else if (!requestDate.equals(other.requestDate)) return false;
      if (responseDate == null) {
        if (other.responseDate != null) return false;
      } else if (!responseDate.equals(other.responseDate)) return false;
      if (user == null) {
        if (other.user != null) return false;
      } else if (!user.equals(other.user)) return false;
    }
    return true;
  }
}
