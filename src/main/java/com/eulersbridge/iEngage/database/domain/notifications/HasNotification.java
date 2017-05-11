package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
//

/**
 * @author Yikai Gong
 */

@RelationshipEntity(type = DataConstants.HAS_NOTIFICATION_LABEL)
public class HasNotification {
  @GraphId
  private Long id;
  @StartNode
  private Node user;
  @EndNode
  private Node notification;

  private Boolean read = false;

  public HasNotification() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser$() {
    return (User) user;
  }

  public void setUser(Node user) {
    this.user = user;
  }

  public Notification getNotification$() {
    return (Notification) notification;
  }

  public Node getUser() {
    return user;
  }

  public Node getNotification() {
    return notification;
  }

  public void setNotification(Node notification) {
    this.notification = notification;
  }

  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    this.read = read;
  }

  @Override
  public String toString() {
    return "HasNotification{" +
      "id=" + id +
      ", userId=" + user.getNodeId() +
      ", notificationId=" + notification.getNodeId() +
      ", read=" + read +
      '}';
  }
}
