package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.badge.BadgeDetails;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Badge extends Node {
  private static final Logger LOG = LoggerFactory.getLogger(Badge.class);

  @Index
  private String name;
  private String description;
  @Index
  private Integer level;
  private Long xpValue;

  public Badge() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public Badge(Long nodeId) {
    super(nodeId);
  }

  public Badge(String name, String description, Integer level, Long xpValue) {
    super();
    this.name = name;
    this.description = description;
    this.level = level;
    this.xpValue = xpValue;
  }

  public static Badge fromBadgeDetails(BadgeDetails badgeDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromBadgeDetails()");
    Badge badge = new Badge();
    if (LOG.isTraceEnabled()) LOG.trace("badgeDetails " + badgeDetails);
    badge.setNodeId(badgeDetails.getNodeId());
    badge.setName(badgeDetails.getName());
    badge.setLevel(badgeDetails.getLevel());
    badge.setDescription(badgeDetails.getDescription());
    badge.setXpValue(badgeDetails.getXpValue());

    if (LOG.isTraceEnabled()) LOG.trace("badge " + badge);
    return badge;
  }

  public BadgeDetails toBadgeDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toBadgeDetails()");
    BadgeDetails badgeDetails = new BadgeDetails();
    if (LOG.isTraceEnabled()) LOG.trace("badge " + this);
    badgeDetails.setNodeId(getNodeId());
    badgeDetails.setName(getName());
    badgeDetails.setLevel(getLevel());
    badgeDetails.setDescription(getDescription());
    badgeDetails.setXpValue(getXpValue());

    if (LOG.isTraceEnabled()) LOG.trace("badgeDetails; " + badgeDetails);
    return badgeDetails;
  }

  @Override
  public String toString() {
    String buff = "[ id = " + getNodeId() +
      ", name = " +
      getName() +
      ", level = " +
      getLevel() +
      ", description = " +
      getDescription() +
      ", xpValue = " +
      getXpValue() +
      " ]";
    String retValue;
    retValue = buff;
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the level
   */
  public Integer getLevel() {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(Integer level) {
    this.level = level;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getXpValue() {
    return xpValue;
  }

  public void setXpValue(Long xpValue) {
    this.xpValue = xpValue;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (this.nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result
        + ((xpValue == null) ? 0 : xpValue.hashCode());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Badge other = (Badge) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null) return false;

      if (name == null) {
        if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;

      if (description == null) {
        if (other.description != null) return false;
      } else if (!description.equals(other.description)) return false;

      if (xpValue == null) {
        if (other.xpValue != null) return false;
      } else if (!xpValue.equals(other.xpValue)) return false;
    }
    return true;
  }

}
