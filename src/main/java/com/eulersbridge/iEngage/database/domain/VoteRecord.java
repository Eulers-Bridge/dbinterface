package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;

@RelationshipEntity(type = DataConstants.VRECORD_LABEL)
public class VoteRecord {
  private static final Logger LOG = LoggerFactory.getLogger(VoteRecord.class);

  @GraphId
  private Long nodeId;
  //	@Fetch
  @StartNode
  private User voter;
  @EndNode
  private Election election;
  private Long date;
  private String location;
  private String qrCode;

  public VoteRecord() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public VoteRecord(String location) {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor(" + location + ')');
    this.location = location;
    date = Calendar.getInstance().getTimeInMillis();
  }

  public VoteRecordDetails toVoteRecordDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toVoteRecordDetails()");

    String email = null;
    Long electionId = null;

    if (getVoter() != null)
      email = getVoter().getEmail();
    if (getElection() != null)
      electionId = getElection().getNodeId();
    VoteRecordDetails details = new VoteRecordDetails(getNodeId(), email, electionId, getDate(), getLocation(), getQrCode());
    details.setNodeId(getNodeId());
    if (LOG.isTraceEnabled()) LOG.trace("voteRecord " + this);

    BeanUtils.copyProperties(this, details);
/*	    details.setElectionId(this.getElection().getNodeId());
      details.setVoterId(this.getVoter().getEmail());
	    if (LOG.isTraceEnabled()) LOG.trace("instDetails "+details);
*/
    return details;
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

  public String getLocation() {
    if (LOG.isDebugEnabled()) LOG.debug("getEnd() = " + location);
    return location;
  }

  /**
   * @return the qrCode
   */
  public String getQrCode() {
    return qrCode;
  }

  /**
   * @param qrCode the qrCode to set
   */
  public void setQrCode(String qrCode) {
    this.qrCode = qrCode;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * @return the voter
   */
  public User getVoter() {
    return voter;
  }

  /**
   * @param voter the voter to set
   */
  public void setVoter(User voter) {
    this.voter = voter;
  }

  /**
   * @return the election
   */
  public Election getElection() {
    return election;
  }

  /**
   * @param election the election to set
   */
  public void setElection(Election election) {
    this.election = election;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "VoteReminder [nodeId=" + nodeId + ", voter=" + voter
      + ", election=" + election + ", date=" + date
      + ", location=" + location + ", qrCode=" + qrCode
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
        + ((election == null) ? 0 : election.hashCode());
      result = prime * result
        + ((location == null) ? 0 : location.hashCode());
      result = prime * result
        + ((qrCode == null) ? 0 : qrCode.hashCode());
      result = prime * result + ((voter == null) ? 0 : voter.hashCode());
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
    VoteRecord other = (VoteRecord) obj;
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
      if (election == null) {
        if (other.election != null)
          return false;
      } else if (!election.equals(other.election))
        return false;
      if (location == null) {
        if (other.location != null)
          return false;
      } else if (!location.equals(other.location))
        return false;
      if (qrCode == null) {
        if (other.qrCode != null)
          return false;
      } else if (!qrCode.equals(other.qrCode))
        return false;
      if (voter == null) {
        if (other.voter != null)
          return false;
      } else if (!voter.equals(other.voter))
        return false;
    }
    return true;
  }
}
