/**
 *
 */
package com.eulersbridge.iEngage.rest.domain;

/**
 * @author Yikai Gong
 */
public class ContactRequestDomain {
  private Long id;
  private Long requestDate;
  private Long responseDate;
  private Boolean accepted = null;

  private UserProfile requesterProfile;
  private UserProfile requestReceiverProfile;

  public ContactRequestDomain() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Long requestDate) {
    this.requestDate = requestDate;
  }

  public Long getResponseDate() {
    return responseDate;
  }

  public void setResponseDate(Long responseDate) {
    this.responseDate = responseDate;
  }

  public Boolean getAccepted() {
    return accepted;
  }

  public void setAccepted(Boolean accepted) {
    this.accepted = accepted;
  }

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
}
