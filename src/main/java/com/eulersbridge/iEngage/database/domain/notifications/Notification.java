/**
 *
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationHelper;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.HashMap;

/**
 * @author Greg Newitt
 */
@NodeEntity
public class Notification extends Node implements NotificationInterface {
  protected final static Logger LOG = LoggerFactory.getLogger(Notification.class);

  // Shift read property to has_notification relationship
//	Boolean read=false;
  Long timestamp;
  String type;
  @Relationship(type = DataConstants.HAS_NOTIFICATION_LABEL, direction = Relationship.INCOMING)
  HasNotification hasNotificationRelationship;
  @Relationship(type = DataConstants.HAS_NOTIFICATION_LABEL, direction = Relationship.INCOMING)
  Node user;

  public Boolean isRead() {
    if (hasNotificationRelationship != null && hasNotificationRelationship.getRead() != null)
      return this.hasNotificationRelationship.getRead();
    else
      return false;
  }

  public Boolean setupForSave(HashMap<String, GraphRepository<?>> repos) {
    if (LOG.isDebugEnabled()) LOG.debug("setupForSave()");
    Boolean response = false;
    UserRepository userRepo = (UserRepository) repos.get(UserRepository.class.getSimpleName());
    if (userRepo != null) {
      if (LOG.isDebugEnabled()) LOG.debug("User Repository available.");

      User result = null;
      if (hasNotificationRelationship != null
        && hasNotificationRelationship.getUser() != null) {
        result = userRepo.findOne(hasNotificationRelationship.getUser().getNodeId());
        if (result != null) {
          response = true;
        }
      } else {
        if (LOG.isErrorEnabled())
          LOG.error("An issue with supplied user.");
      }

    }
    return response;
  }

  public NotificationDetails toNotificationDetails() {
    if (LOG.isDebugEnabled()) LOG.debug("toNotificationDetails()");
    Long userId = null;
    if (hasNotificationRelationship != null && hasNotificationRelationship.getUser() != null)
      userId = hasNotificationRelationship.getUser().getNodeId();
    NotificationDetails details = new NotificationDetails(nodeId, userId, timestamp, isRead(), type, null);
    return details;
  }

  public static Notification fromNotificationDetails(NotificationDetails nDets) {
    Notification notif;
    if (nDets != null) {
      notif = NotificationHelper.notificationFactory(nDets);
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

    } else {
      notif = null;
    }
    return notif;
  }

  /**
   * @return the nodeId
   */
  public Long getNodeId() {
    return nodeId;
  }


  /**
   * @param nodeId the nodeId to set
   */
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }


  /**
   * @return the read
   */
  public Boolean getRead() {
    return this.isRead();
  }


  /**
   * @param read the read to set
   */
  public void setRead(Boolean read) {
    //Fix me
    if (this.hasNotificationRelationship == null)
      this.hasNotificationRelationship = new HasNotification();
    this.hasNotificationRelationship.setRead(read);
  }

  /**
   * @return the timestamp
   */
  public Long getTimestamp() {
    return timestamp;
  }


  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }


  /**
   * @return the type
   */
  public String getType() {
    return type;
  }


  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Notification [nodeId=" + nodeId + ", read=" + hasNotificationRelationship.getRead()
      + ", timestamp=" + timestamp + ", type=" + type +
      ", hasNotification=" + hasNotificationRelationship.getId() + "]";
  }

  public HasNotification getHasNotificationRelationship() {
    return hasNotificationRelationship;
  }

  public void setHasNotificationRelationship(HasNotification hasNotificationRelationship) {
    this.hasNotificationRelationship = hasNotificationRelationship;
  }

  public Node getUser() {
    return user;
  }

  public User getUser$(){
    return (User) user;
  }

  public void setUser(Node user) {
    this.user = user;
  }
}
