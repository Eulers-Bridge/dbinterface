package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Greg Newitt
 */
@NodeEntity
public class Owner extends Node {

  private static Logger LOG = LoggerFactory.getLogger(Election.class);

  public Owner() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public Owner(Long nodeId) {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    this.nodeId = nodeId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Owner other = (Owner) obj;
    if (nodeId == null) {
      if (other.nodeId != null) return false;
    } else if (!nodeId.equals(other.nodeId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "Owner [nodeId=" + nodeId + "]";
  }

}
