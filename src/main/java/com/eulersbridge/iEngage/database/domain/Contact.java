package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import org.neo4j.ogm.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Newitt
 */
@RelationshipEntity(type = DataConstants.CONTACT_LABEL)
public class Contact {
  @GraphId
  private Long id;
  @Index
  private Long timestamp;

  @StartNode
  private User contactor;
  @EndNode
  private User contactee;

  private static Logger LOG = LoggerFactory.getLogger(Contact.class);

  public Contact() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public ContactDetails toContactDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toContactDetails()");

    Long contactorId = null;
    Long contacteeId = null;

    if (getContactor() != null)
      contactorId = getContactor().getNodeId();
    if (getContactee() != null)
      contacteeId = getContactee().getNodeId();
    ContactDetails details = new ContactDetails(getId(), contactorId, contacteeId, getTimestamp());
    if (LOG.isTraceEnabled()) LOG.trace("Contact " + this);

    return details;
  }

  static public Contact fromContactDetails(ContactDetails details) {
    if (LOG.isTraceEnabled()) LOG.trace("toContactDetails()");
    Contact contact = null;
    User contactor;
    User contactee;
    if (details != null) {
      contact = new Contact();
      contact.setId(details.getNodeId());
      contact.setTimestamp(details.getTimestamp());
      contactor = new User(details.getContactorId());
      contactee = new User(details.getContacteeId());
      contact.setContactee(contactee);
      contact.setContactor(contactor);
    }


    if (LOG.isTraceEnabled()) LOG.trace("Contact " + contact);

    return contact;
  }

  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  public Long getTimestamp() {
    if (LOG.isDebugEnabled()) LOG.debug("getTimestamp() = " + timestamp);
    return timestamp;
  }

  /**
   * @param timestamp the date to set
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

}
