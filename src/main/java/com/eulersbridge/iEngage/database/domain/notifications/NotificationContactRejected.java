/**
 *
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.HashMap;

/**
 * @author Greg Newitt
 */
public class NotificationContactRejected extends Notification implements NotificationInterface {
  //	@Fetch
  @Relationship(type = DatabaseDomainConstants.HAS_NOTIFICATION_DETAILS_LABEL, direction = Relationship.OUTGOING)
  ContactRequest contactRequest;

  static Logger LOG = LoggerFactory.getLogger(NotificationContactRejected.class);


  @Override
  public Boolean setupForSave(HashMap<String, GraphRepository<?>> repos) {
    if (LOG.isDebugEnabled()) LOG.debug("setupForSave()");
    Boolean response = false;
    ContactRequest result = null;
    response = super.setupForSave(repos);
    if (response) {
      if (LOG.isDebugEnabled()) LOG.debug("Super method successful.");
      response = false;
      ContactRequestRepository crRepo = (ContactRequestRepository) repos.get(ContactRequestRepository.class.getSimpleName());
      if (crRepo != null) {
        if (LOG.isDebugEnabled())
          LOG.debug("CR repository retrieved. - " + getContactRequest());
        if (getContactRequest() != null) {
          if (getContactRequest().getNodeId() != null) {
            if (LOG.isDebugEnabled()) LOG.debug("NodeId present. - ");
            result = crRepo.findOne(getContactRequest().getNodeId());
          } else if ((getContactRequest().getUser() != null) && (getContactRequest().getUser().getNodeId() != null) && (getContactRequest().getContactDetails() != null)) {
            if (LOG.isDebugEnabled())
              LOG.debug("other info present - " + getContactRequest().getUser().getNodeId() + " , " + getContactRequest().getContactDetails());
            result = crRepo.findContactRequestByUserIdContactInfo(getContactRequest().getUser().getNodeId(), getContactRequest().getContactDetails());
          }
          if (result != null) {
            setContactRequest(result);
            response = true;
          }
        }
      }
    }
    return response;

  }

  @Override
  public NotificationDetails toNotificationDetails() {
    if (LOG.isDebugEnabled()) LOG.debug("toNotificationDetails()");
    NotificationDetails details = super.toNotificationDetails();
    ContactRequestDetails contactRequestDetails = contactRequest.toContactRequestDetails();
    details.setNotificationBody(contactRequestDetails);
    return details;
  }

  public static NotificationContactRejected fromNotificationDetails(NotificationDetails nDets) {
    NotificationContactRejected notif = new NotificationContactRejected();
    if (nDets != null) {
      if (NotificationConstants.CONTACT_REJECTED.equals(nDets.getType())) {
        ContactRequestDetails crd = (ContactRequestDetails) nDets.getNotificationBody();
        ContactRequest cr = ContactRequest.fromContactRequestDetails(crd);
        notif.setContactRequest(cr);
      }
      notif.setType(nDets.getType());
      notif.setNodeId(nDets.getNodeId());
      User user = new User(nDets.getUserId());
      HasNotification hasNotification = new HasNotification();
      hasNotification.setNotification(notif);
      hasNotification.setUser(user.toNode());
      notif.setHasNotificationRelationship(hasNotification);
      notif.setUser(user.toNode());
      notif.setRead(nDets.getRead());
      notif.setTimestamp(nDets.getTimestamp());
    }
    return notif;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "NotificationContactRejected [contactRequest=" + contactRequest
      + ", nodeId=" + nodeId + ", read=" + isRead() + ", timestamp="
      + timestamp + ", type=" + type + "]";
  }

  public static ContactRequestDetails populateFields(JsonNode node) throws JsonMappingException {
    return NotificationContactRequest.populateFields(node);
  }

  /**
   * @return the contactRequest
   */
  public ContactRequest getContactRequest() {
    return contactRequest;
  }

  /**
   * @param contactRequest the contactRequest to set
   */
  public void setContactRequest(ContactRequest contactRequest) {
    this.contactRequest = contactRequest;
  }

}
