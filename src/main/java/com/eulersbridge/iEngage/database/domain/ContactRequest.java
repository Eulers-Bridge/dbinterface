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
  @GraphId
  private Long id;
  private Long requestDate;
  private Long responseDate;
  private Boolean accepted;

  @StartNode
  private Node creator;
  @EndNode
  private Node target;

  public ContactRequest() {
  }

  public ContactRequestDomain toDomain() {
    ContactRequestDomain domain = new ContactRequestDomain();
    domain.setId(id);
    domain.setRequestDate(requestDate);
    domain.setResponseDate(responseDate);
    domain.setAccepted(accepted);
    domain.setRequesterProfile(UserProfile.fromUserDetails(getCreator$().toUserDetails()));
    domain.setRequestReceiverProfile(UserProfile.fromUserDetails(getTarget$().toUserDetails()));

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

  public Node getCreator() {
    return creator;
  }

  public User getCreator$() {
    if (creator instanceof User)
      return (User) creator;
    return new User(creator.nodeId);
  }

  public void setCreator(Node creator) {
    this.creator = creator;
  }

  public Node getTarget() {
    return target;
  }

  public User getTarget$() {
    if (target instanceof User)
      return (User) target;
    return new User(target.nodeId);
  }

  public void setTarget(Node target) {
    this.target = target;
  }
}
