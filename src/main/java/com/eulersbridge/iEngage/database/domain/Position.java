package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Position extends Node {
  private static final Logger LOG = LoggerFactory.getLogger(Position.class);

  private String name;
  private String description;

  @Relationship(type = DataConstants.HAS_POSITION_LABEL, direction = Relationship.UNDIRECTED)
  private Node election;

  public Position() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public Position(Long nodeId) {
    super(nodeId);
  }

  public static Position fromPositionDetails(PositionDetails positionDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromPositionDetails()");
    Position position = new Position();
    if (LOG.isTraceEnabled()) LOG.trace("positionDetails " + positionDetails);
    position.setNodeId(positionDetails.getNodeId());
    position.setName(positionDetails.getName());
    position.setDescription(positionDetails.getDescription());
    Node election = new Node(positionDetails.getElectionId());
    position.setElection(election);

    if (LOG.isTraceEnabled()) LOG.trace("position " + position);
    return position;
  }

  public PositionDetails toPositionDetails() {
    PositionDetails positionDetails = new PositionDetails();
    positionDetails.setNodeId(getNodeId());
    positionDetails.setName(getName());
    positionDetails.setDescription(getDescription());

    if (election != null)
      positionDetails.setElectionId(election.getNodeId());
    else
      positionDetails.setElectionId(null);

    return positionDetails;
  }

  @Override
  public String toString() {
    String buff = "[ id = " + getNodeId() +
      ", name = " +
      getName() +
      ", description = " +
      getDescription() +
      ", election = " +
      getElection() +
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Election getElection$() {
    return (Election) election;
  }

  public Node getElection() {
    return election;
  }

  public void setElection(Node election) {
    this.election = election;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (this.nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result
        + ((description == null) ? 0 : description.hashCode());
      result = prime * result
        + ((election == null) ? 0 : election.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Position other = (Position) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;

      if (description == null) {
        if (other.description != null) return false;
      } else if (!description.equals(other.description)) return false;
      if (election == null) {
        if (other.election != null) return false;
      } else if (!election.equals(other.election)) return false;
      if (name == null) {
        if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
    }
    return true;
  }
}
