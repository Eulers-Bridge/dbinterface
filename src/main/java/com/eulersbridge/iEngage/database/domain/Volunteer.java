package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NodeEntity
public class Volunteer extends Node {
  private static final Logger LOG = LoggerFactory.getLogger(Volunteer.class);

  private String title;
  private String description;

  public Volunteer() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public Volunteer(String title, String description) {
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + title + ',' + description + ')');
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    if (LOG.isDebugEnabled()) LOG.debug("getTitle() = " + title);
    return title;
  }

  public String getDescription() {
    if (LOG.isDebugEnabled()) LOG.debug("getDescription() = " + description);
    return description;
  }

  public String toString() {
    StringBuffer buff = new StringBuffer("[ nodeId = ");
    String retValue;
    buff.append(getNodeId());
    buff.append(", title = ");
    buff.append(getTitle());
    buff.append(", description = ");
    buff.append(getDescription());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

}
