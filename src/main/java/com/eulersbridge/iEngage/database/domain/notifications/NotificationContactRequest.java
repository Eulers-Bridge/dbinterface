/**
 *
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationHelper;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Greg Newitt
 */
public class NotificationContactRequest extends Notification implements NotificationInterface {
  @Relationship(type = DataConstants.HAS_NOTIFICATION_DETAILS_LABEL, direction = Relationship.OUTGOING)
  ContactRequest contactRequest;

  static Logger LOG = LoggerFactory.getLogger(NotificationContactRequest.class);

  private static String[] contactRequestFieldsArray = {
    NotificationConstants.NodeId,
    NotificationConstants.RequestDate,
    NotificationConstants.ResponseDate,
    NotificationConstants.UserId,
    NotificationConstants.Accepted,
    NotificationConstants.Rejected,
    NotificationConstants.ContactDetails
  };


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
    NotificationDetails details = super.toNotificationDetails();
    ContactRequestDetails contactRequestDetails = contactRequest.toContactRequestDetails();
    details.setNotificationBody(contactRequestDetails);
    return details;
  }

  public static NotificationContactRequest fromNotificationDetails(NotificationDetails nDets) {
    NotificationContactRequest notif = new NotificationContactRequest();
    if (nDets != null) {
      if (NotificationConstants.CONTACT_REQUEST.equals(nDets.getType())) {
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

  static public ContactRequestDetails populateFields(JsonNode node) throws JsonMappingException {
    Iterator<String> fields = node.fieldNames();
    NotificationHelper.checkFieldNames(fields, contactRequestFieldsArray);

    Long crUserId = NotificationHelper.getLong(node.get(NotificationConstants.UserId));
    Long crNodeId = NotificationHelper.getLong(node.get(NotificationConstants.NodeId));
    Long requestDate = NotificationHelper.getLong(node.get(NotificationConstants.RequestDate));
    Long responseDate = NotificationHelper.getLong(node.get(NotificationConstants.ResponseDate));
    Boolean accepted = NotificationHelper.getBoolean(node.get(NotificationConstants.Accepted));
    Boolean rejected = NotificationHelper.getBoolean(node.get(NotificationConstants.Rejected));
    JsonNode contactDetails = node.get(NotificationConstants.ContactDetails);
    String contactDets = null;
    if (contactDetails != null) contactDets = contactDetails.asText();
    ContactRequestDetails crd = new ContactRequestDetails(crNodeId, contactDets, requestDate, responseDate, accepted, rejected, crUserId, null);
    if ((null == crNodeId) && ((null == contactDetails) && (null == crUserId)))
      throw new JsonMappingException("notificationBody must be populated with a contactRequest containing nodeId or contactDetails and contactor UserId");
    return crd;
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "NotificationContactRequest [contactRequest=" + contactRequest
      + ", nodeId=" + nodeId + ", read=" + isRead() + ", timestamp="
      + timestamp + ", type=" + type + "]";
  }

}
