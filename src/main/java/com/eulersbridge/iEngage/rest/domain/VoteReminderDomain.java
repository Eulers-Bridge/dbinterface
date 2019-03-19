/**
 *
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.rest.controller.UserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Greg Newitt
 */
public class VoteReminderDomain extends ResourceSupport {
  private Long nodeId;
  private String userEmail;
  private Long electionId;
  private Long date;
  private String location;
  private Long timestamp;

  public VoteReminderDetails toVoteReminderDetails() {
    VoteReminderDetails vrd = new VoteReminderDetails(nodeId, userEmail, electionId, date, location, timestamp);
    return vrd;
  }

  public static VoteReminderDomain fromVoteReminderDetails(
    VoteReminderDetails voteReminderDetails) {
    VoteReminderDomain vr = new VoteReminderDomain();
    vr.setUserEmail(voteReminderDetails.getUserId());
    vr.setDate(voteReminderDetails.getDate());
    vr.setLocation(voteReminderDetails.getLocation());
    vr.setNodeId(voteReminderDetails.getNodeId());
    vr.setTimestamp(voteReminderDetails.getTimestamp());
    vr.setElectionId(voteReminderDetails.getElectionId());

    String simpleName = UserDomain.class.getSimpleName();
    String name = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);

    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
    //this section is all that is actually needed in our model to add hateoas support.

    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
    //We have explicitly avoided that.
    // {!begin selfRel}
    vr.add(linkTo(UserController.class).slash(name).slash(vr.getUserEmail()).slash("voteReminder").withSelfRel());
    // {!end selfRel}
    return vr;
  }

  /**
   * @return the nodeId
   */
  public Long getNodeId() {
    return nodeId;
  }

  /**
   * @param nodeId the nodeId to set
   */
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  /**
   * @return the userEmail
   */
  public String getUserEmail() {
    return userEmail;
  }

  /**
   * @param userEmail the userEmail to set
   */
  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  /**
   * @return the electionId
   */
  public Long getElectionId() {
    return electionId;
  }

  /**
   * @param electionId the electionId to set
   */
  public void setElectionId(Long electionId) {
    this.electionId = electionId;
  }

  /**
   * @return the date
   */
  public Long getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Long date) {
    this.date = date;
  }

  /**
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
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

  public static Iterator<VoteReminderDomain> toVoteRemindersIterator(
    Iterator<? extends Details> iterator) {
    if (null == iterator) return null;
    ArrayList<VoteReminderDomain> elections = new ArrayList<VoteReminderDomain>();
    while (iterator.hasNext()) {
      VoteReminderDetails dets = (VoteReminderDetails) iterator.next();
      VoteReminderDomain thisTicket = VoteReminderDomain.fromVoteReminderDetails(dets);
      Link self = thisTicket.getLink("self");
      thisTicket.removeLinks();
      thisTicket.add(self);
      elections.add(thisTicket);
    }
    return elections.iterator();
  }

  public boolean isValidInput() {
    return userEmail != null && electionId != null
      && location != null && date != null
      && !userEmail.isEmpty() && !location.isEmpty()
      && date > System.currentTimeMillis();
  }
}
