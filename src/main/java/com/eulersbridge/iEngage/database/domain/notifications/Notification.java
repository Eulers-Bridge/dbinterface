/**
 *
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationHelper;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
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
  @Relationship(type = DatabaseDomainConstants.HAS_NOTIFICATION_LABEL, direction = Relationship.INCOMING)
  Node user;
  @Relationship(type = DatabaseDomainConstants.HAS_NOTIFICATION_LABEL, direction = Relationship.INCOMING)
  HasNotification hasNotificationRelationship;

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
      if (getUser() != null) {
        if (LOG.isDebugEnabled()) LOG.debug("User available." + getUser());
        User result = null;
        if (getUser().getNodeId() != null)
          result = userRepo.findOne(getUser().getNodeId());
        else if (getUser().getEmail() != null)
          result = userRepo.findByEmail(getUser().getEmail());


        if (result != null) {
          // shift user to HasNotification obj
          // set Notification level user to null to avoid duplicated relationship link during save
          user = null;
          hasNotificationRelationship.setUser(result);
          response = true;
        }
      } else {
        if (LOG.isErrorEnabled())
          LOG.error("An issue with supplied user." + getUser());
      }
    }
    return response;
  }

  public NotificationDetails toNotificationDetails() {
    if (LOG.isDebugEnabled()) LOG.debug("toNotificationDetails()");

    Long userId = null;
    if (user != null) userId = user.getNodeId();

    NotificationDetails dets = new NotificationDetails(nodeId, userId, timestamp, isRead(), type, null);
    return dets;
  }

  public static Notification fromNotificationDetails(NotificationDetails nDets) {
    Notification notif;
    if (nDets != null) {
      notif = NotificationHelper.notificationFactory(nDets);
      notif.setType(nDets.getType());
      notif.setNodeId(nDets.getNodeId());
      User user = new User(nDets.getUserId());
      notif.setUser(user);

      HasNotification hasNotification = new HasNotification();
      hasNotification.setNotification(notif);
      hasNotification.setUser(user);
      notif.setHasNotificationRelationship(hasNotification);

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
    return "Notification [nodeId=" + nodeId + ", read=" + hasNotificationRelationship.getRead()
      + ", timestamp=" + timestamp + ", type=" + type + ", user="
      + user + ", hasNotification=" + hasNotificationRelationship.getId() + "]";
  }

  public HasNotification getHasNotificationRelationship() {
    return hasNotificationRelationship;
  }

  public void setHasNotificationRelationship(HasNotification hasNotificationRelationship) {
    this.hasNotificationRelationship = hasNotificationRelationship;
  }
}
