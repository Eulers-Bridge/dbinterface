package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author Greg Newitt
 */
@NodeEntity
public class Location extends Likeable {

  public Location() {
    super();
  }

  public Location(Long nodeId) {
    super();
    this.nodeId = nodeId;
  }
}
