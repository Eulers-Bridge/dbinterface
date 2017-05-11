package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.badge.BadgeCompleteDetails;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;


@RelationshipEntity(type = DataConstants.BADGE_COMPLETE_LABEL)
public class BadgeComplete {
  private static final Logger LOG = LoggerFactory.getLogger(BadgeComplete.class);

  @GraphId
  private Long nodeId;
  @StartNode
  private User user;
  @EndNode
  private Badge badge;
  private Long date;

  public BadgeComplete() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor()");
    date = Calendar.getInstance().getTimeInMillis();
  }

  public BadgeCompleteDetails toBadgeCompleteDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toBadgeCompleteDetails()");

    Long userId = ((getUser() == null) ? null : getUser().getNodeId());
    Long badgeId = ((getBadge() == null) ? null : getBadge().getNodeId());
    BadgeCompleteDetails details = new BadgeCompleteDetails(getNodeId(), userId, badgeId, getDate());
    details.setNodeId(getNodeId());
    if (LOG.isTraceEnabled()) LOG.trace("taskComplete " + this);

    BeanUtils.copyProperties(this, details);
    details.setBadgeId(badgeId);
    details.setUserId(userId);
    if (LOG.isTraceEnabled()) LOG.trace("instDetails " + details);

    return details;
  }

  public static BadgeComplete fromBadgeCompleteDetails(BadgeCompleteDetails details) {
    if (LOG.isTraceEnabled()) LOG.trace("fromBadgeCompleteDetails()");

    BadgeComplete badgeComplete = new BadgeComplete();
    badgeComplete.setNodeId(details.getNodeId());
    badgeComplete.setDate(details.getDate());
    Badge badge = new Badge();
    badge.setNodeId(details.getBadgeId());
    badgeComplete.setBadge(badge);
    User user = new User();
    user.setNodeId(details.getUserId());
    badgeComplete.setUser(user);

    if (LOG.isTraceEnabled())
      LOG.trace("taskComplete " + badgeComplete + " taskCompleteDetails " + details);
    return badgeComplete;
  }

  public Long getNodeId() {
    return nodeId;
  }

  /**
   * @param nodeId the nodeId to set
   */
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public Long getDate() {
    if (LOG.isDebugEnabled()) LOG.debug("getYear() = " + date);
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Long date) {
    this.date = date;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * @return the badge
   */
  public Badge getBadge() {
    return badge;
  }

  /**
   * @param badge the badge to set
   */
  public void setBadge(Badge badge) {
    this.badge = badge;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "BadgeComplete [nodeId=" + nodeId + ", user=" + user
      + ", badge=" + badge + ", date=" + date
      + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (getNodeId() == null) {
      result = prime * result + ((date == null) ? 0 : date.hashCode());
      result = prime * result
        + ((badge == null) ? 0 : badge.hashCode());
      result = prime * result + ((user == null) ? 0 : user.hashCode());
    } else {
      result = getNodeId().hashCode();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BadgeComplete other = (BadgeComplete) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;
      if (date == null) {
        if (other.date != null)
          return false;
      } else if (!date.equals(other.date))
        return false;
      if (badge == null) {
        if (other.badge != null)
          return false;
      } else if (!badge.equals(other.badge))
        return false;
      if (user == null) {
        if (other.user != null)
          return false;
      } else if (!user.equals(other.user))
        return false;
    }
    return true;
  }
}
