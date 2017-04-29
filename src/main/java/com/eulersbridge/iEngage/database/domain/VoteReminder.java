package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;

@RelationshipEntity(type = DatabaseDomainConstants.VREMINDER_LABEL)
public class VoteReminder {

  @GraphId
  private Long nodeId;
  //	@Fetch
  @StartNode
  private User voter;
  @EndNode
  private Election election;
  private Long date;
  private String location;
  private Long timestamp;

  private static Logger LOG = LoggerFactory.getLogger(VoteReminder.class);

  public VoteReminder() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public VoteReminder(Long date, String location) {
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + date + ',' + location + ')');
    this.date = date;
    this.location = location;
    timestamp = Calendar.getInstance().getTimeInMillis();
  }

  public static VoteReminder fromVoteReminderDetails(VoteReminderDetails dets) {
    if (LOG.isTraceEnabled()) LOG.trace("fromVoteReminderDetails()");
    VoteReminder voteReminder = new VoteReminder();
    if (LOG.isTraceEnabled()) LOG.trace("dets " + dets);
    voteReminder.setNodeId(dets.getElectionId());
    voteReminder.setLocation(dets.getLocation());
    voteReminder.setDate(dets.getDate());
    voteReminder.setTimestamp(dets.getTimestamp());
    Election election = new Election();
    election.setNodeId(dets.getNodeId());
    voteReminder.setElection(election);
    User user = new User();
    user.setEmail(dets.getUserId());
    voteReminder.setVoter(user);
    if (LOG.isTraceEnabled()) LOG.trace("voteReminder " + voteReminder);
    return voteReminder;
  }

  public VoteReminderDetails toVoteReminderDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toVoteReminderDetails()");

    VoteReminderDetails details = new VoteReminderDetails();
    details.setNodeId(getNodeId());
    if (LOG.isTraceEnabled()) LOG.trace("voteRecord " + this);

    BeanUtils.copyProperties(this, details);
    details.setElectionId(this.getElection().getNodeId());
    details.setUserId(this.getVoter().getEmail());
    if (LOG.isTraceEnabled()) LOG.trace("instDetails " + details);

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
    if (LOG.isDebugEnabled()) LOG.debug("getDate() = " + date);
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "VoteReminder [nodeId=" + nodeId + ", voter=" + voter
      + ", election=" + election + ", date=" + date +
      ", location=" + location + ", timestamp=" + timestamp
      + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (getNodeId() != null) {
      result = getNodeId().hashCode();
    } else {
      result = prime * result + ((date == null) ? 0 : date.hashCode());
      result = prime * result
        + ((election == null) ? 0 : election.hashCode());
      result = prime * result
        + ((location == null) ? 0 : location.hashCode());
      result = prime * result
        + ((timestamp == null) ? 0 : timestamp.hashCode());
      result = prime * result + ((voter == null) ? 0 : voter.hashCode());
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VoteReminder other = (VoteReminder) obj;
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
      if (timestamp == null) {
        if (other.timestamp != null)
          return false;
      } else if (!timestamp.equals(other.timestamp))
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
