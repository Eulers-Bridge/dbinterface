package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.rest.domain.ContactRequestDomain;
import com.eulersbridge.iEngage.rest.domain.UserProfile;
import org.neo4j.ogm.annotation.*;

/**
 * @author Yikai Gong
 */
@RelationshipEntity(type = DataConstants.CONTACT_REQUEST_LABEL)
public class ContactRequest {
  @Id @GeneratedValue
  private Long id;
  private Long requestDate;
  private Long responseDate;
  @Index
  private Boolean accepted;

  @StartNode
  private User creator;
  @EndNode
  private User target;


  public ContactRequest() {
  }

  public ContactRequestDomain toDomain() {
    ContactRequestDomain domain = new ContactRequestDomain();
    domain.setId(id);
    domain.setRequestDate(requestDate);
    domain.setResponseDate(responseDate);
    domain.setAccepted(accepted);
    domain.setRequesterProfile(UserProfile.fromUserDetails(getCreator().toUserDetails()));
    domain.setRequestReceiverProfile(UserProfile.fromUserDetails(getTarget().toUserDetails()));

    return domain;
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

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getTarget() {
    return target;
  }

  public void setTarget(User target) {
    this.target = target;
  }
}
