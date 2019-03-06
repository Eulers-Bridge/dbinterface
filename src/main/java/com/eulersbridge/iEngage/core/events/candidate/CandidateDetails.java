package com.eulersbridge.iEngage.core.events.candidate;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CandidateDetails extends Details {
  private String information;
  private String policyStatement;
  private Iterable<PhotoDetails> photos;
  private Long userId;
  private String email;
  private String givenName;
  private String familyName;
  private Long positionId;
  private TicketDetails ticketDetails;
  private UserDetails userDetails;

  private static Logger LOG = LoggerFactory.getLogger(CandidateDetails.class);

  @Override
  public String toString() {
    StringBuffer buff = new StringBuffer("[ id = ");
    String retValue;
    buff.append(getNodeId());
    buff.append(", information = ");
    buff.append(getInformation());
    buff.append(", policyStatement = ");
    buff.append(getPolicyStatement());
    buff.append(", photos = ");
    buff.append(getPhotos());
    buff.append(", userId = ");
    buff.append(getUserId());
    buff.append(", email = ");
    buff.append(getEmail());
    buff.append(", positionId = ");
    buff.append(getPositionId());
    buff.append(", ticket = ");
    if (null == getTicketDetails())
      buff.append("null");
    else buff.append(getTicketDetails().toString());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (getNodeId() != null) {
      result = prime * result + getNodeId().hashCode();
    } else {
      result = prime * result + ((information == null) ? 0 : information.hashCode());
      result = prime * result + ((policyStatement == null) ? 0 : policyStatement.hashCode());
      result = prime * result + ((photos == null) ? 0 : photos.hashCode());
      result = prime * result + ((userId == null) ? 0 : userId.hashCode());
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CandidateDetails other = (CandidateDetails) obj;
    if (nodeId != null) {
      if (nodeId.equals(other.nodeId))
        return true;
      else return false;
    } else {
      if (other.nodeId != null)
        return false;
      if (information == null) {
        if (other.information != null)
          return false;
      } else if (!information.equals(other.information))
        return false;
      if (policyStatement == null) {
        if (other.policyStatement != null)
          return false;
      } else if (!policyStatement.equals(other.policyStatement))
        return false;
      if (email == null) {
        if (other.email != null)
          return false;
      } else if (!email.equals(other.email))
        return false;
      if (userId == null) {
        if (other.userId != null)
          return false;
      } else if (!userId.equals(other.userId))
        return false;
      if (positionId == null) {
        if (other.positionId != null)
          return false;
      } else if (!positionId.equals(other.positionId))
        return false;
    }
    return true;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public String getPolicyStatement() {
    return policyStatement;
  }

  public void setPolicyStatement(String policyStatement) {
    this.policyStatement = policyStatement;
  }

  /**
   * @return the userId
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the familyName
   */
  public String getFamilyName() {
    return familyName;
  }

  /**
   * @param familyName the familyName to set
   */
  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  /**
   * @return the givenName
   */
  public String getGivenName() {
    return givenName;
  }

  /**
   * @param givenName the givenName to set
   */
  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  /**
   * @return the positionId
   */
  public Long getPositionId() {
    return positionId;
  }

  /**
   * @param positionId the positionId to set
   */
  public void setPositionId(Long positionId) {
    this.positionId = positionId;
  }

  public TicketDetails getTicketDetails() {
    return ticketDetails;
  }

  public void setTicketDetails(TicketDetails ticketDetails) {
    this.ticketDetails = ticketDetails;
  }

  /**
   * @return the photos
   */
  public Iterable<PhotoDetails> getPhotos() {
    return photos;
  }

  /**
   * @param photos the photos to set
   */
  public void setPhotos(Iterable<PhotoDetails> photos) {
    this.photos = photos;
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }
}
