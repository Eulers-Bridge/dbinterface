package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.rest.domain.VoteReminderDomain;
import org.neo4j.ogm.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;

@RelationshipEntity(type = DataConstants.VREMINDER_LABEL)
public class VoteReminder {
  private static final Logger LOG = LoggerFactory.getLogger(VoteReminder.class);

  @Id @GeneratedValue
  private Long nodeId;
  //	@Fetch
  @StartNode
  private User voter;
  @EndNode
  private Election election;
  private Long date;
  private String location;
  private Long timestamp;

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
    details.setUserEmail(this.getVoter().getEmail());
    if (LOG.isTraceEnabled()) LOG.trace("instDetails " + details);

    return details;
  }

  public VoteReminderDomain toDomain(){
    VoteReminderDomain domain = new VoteReminderDomain();
    domain.setNodeId(nodeId);
    domain.setDate(date);
    domain.setTimestamp(timestamp);
    domain.setLocation(location);
    if (voter!=null)
      domain.setUserEmail(voter.getEmail());
    if (election!=null)
      domain.setElectionId(election.getNodeId());
    return domain;
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


  @Override
  public String toString() {
    return "VoteReminder [nodeId=" + nodeId + ", voter=" + voter
      + ", election=" + election + ", date=" + date +
      ", location=" + location + ", timestamp=" + timestamp
      + "]";
  }
}
