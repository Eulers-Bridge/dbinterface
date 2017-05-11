package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Newitt
 */
@NodeEntity
public class VotingLocation extends Location {
  private static final Logger LOG = LoggerFactory.getLogger(VotingLocation.class);

  private String name;
  private String information;
  @Relationship(type = DataConstants.HAS_VOTING_LOCATION_LABEL, direction = Relationship.INCOMING)
  private Owner owner;


  public VotingLocation() {
    super();
  }

  public VotingLocation(Long nodeId, String name, String information,
                        Owner owner) {
    super(nodeId);
    this.name = name;
    this.information = information;
    this.owner = owner;
  }

  public static VotingLocation fromVotingLocationDetails(VotingLocationDetails locationDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromVotingLocationDetails()");
    VotingLocation location = new VotingLocation();
    if (LOG.isTraceEnabled())
      LOG.trace("votingLocationDetails " + locationDetails);
    location.setNodeId(locationDetails.getNodeId());
    location.setName(locationDetails.getName());
    location.setInformation(locationDetails.getInformation());
    Owner owner = new Owner(locationDetails.getOwnerId());
    location.setOwner(owner);
    if (LOG.isTraceEnabled()) LOG.trace("votingLocation " + location);
    return location;
  }

  public VotingLocationDetails toVotingLocationDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toVotingLocationDetails()");
    VotingLocationDetails votingLocationDetails = new VotingLocationDetails();
    if (LOG.isTraceEnabled()) LOG.trace("votingLocation " + this);
    votingLocationDetails.setNodeId(getNodeId());
    votingLocationDetails.setName(getName());
    votingLocationDetails.setInformation(getInformation());
    votingLocationDetails.setOwnerId(getOwner().getNodeId());

    if (LOG.isTraceEnabled())
      LOG.trace("votingLocationDetails; " + votingLocationDetails);
    return votingLocationDetails;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the information
   */
  public String getInformation() {
    return information;
  }

  /**
   * @param information the information to set
   */
  public void setInformation(String information) {
    this.information = information;
  }

  /**
   * @return the owner
   */
  public Owner getOwner() {
    return owner;
  }

  /**
   * @param owner the owner to set
   */
  public void setOwner(Owner owner) {
    this.owner = owner;
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
    if (getNodeId() != null) {
      result = prime * result + getNodeId().hashCode();
    } else {
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result
        + ((information == null) ? 0 : information.hashCode());
      result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
    VotingLocation other = (VotingLocation) obj;
    if (getNodeId() != null) {
      return getNodeId().equals(other.getNodeId());
    } else {
      if (other.getNodeId() != null) return false;

      if (name == null) {
        if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;

      if (information == null) {
        if (other.information != null) return false;
      } else if (!information.equals(other.information)) return false;
      if (owner == null) {
        if (other.owner != null) return false;
      } else if (!owner.equals(other.owner)) return false;
    }
    return true;
  }

}
