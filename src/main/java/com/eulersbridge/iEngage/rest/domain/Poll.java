package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.rest.controller.PollController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Poll extends ResourceSupport {
  private Long nodeId;
  private String question;
  private List<PollOptionDomain> pollOptions;
  private Long start;
  private Long duration;
  private Long ownerId;
  private Long creatorId;
  private String creatorEmail;
  private String image;
  private Boolean closed;

//    private Integer numOfComments;
//    private Integer numOfAnswers;

  private static Logger LOG = LoggerFactory.getLogger(Poll.class);

  public static Poll fromPollDetails(PollDetails pollDetails) {
    Poll poll = new Poll();
    String simpleName = Poll.class.getSimpleName();
    String name = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    poll.setNodeId(pollDetails.getPollId());
    poll.setQuestion(pollDetails.getQuestion());
    poll.setStart(pollDetails.getStart());
    poll.setDuration(pollDetails.getDuration());
    poll.setOwnerId(pollDetails.getOwnerId());
    poll.setCreatorId(pollDetails.getCreatorId());
    poll.setCreatorEmail(pollDetails.getCreatorEmail());
    poll.setImage(pollDetails.getImage());
    poll.setPollOptions(pollDetails.getPollOptions());
    poll.setClosed(pollDetails.getClosed());
    poll.add(linkTo(PollController.class).slash(name).slash(poll.getNodeId()).withSelfRel());
    poll.add(linkTo(PollController.class).slash(name + 's').withRel(RestDomainConstants.READALL_LABEL));
    return poll;
  }

  public PollDetails toPollDetails() {
    PollDetails pollDetails = new PollDetails();
//        BeanUtils.copyProperties(pollDetails, this);
//    pollDetails.setPollId(this.getId());
    pollDetails.setCreatorEmail(creatorEmail);
    pollDetails.setQuestion(this.getQuestion());
    pollDetails.setStart(this.getStart());
    pollDetails.setDuration(this.getDuration());
    pollDetails.setOwnerId(getOwnerId());
    pollDetails.setCreatorId(getCreatorId());
    pollDetails.setImage(getImage());
    pollDetails.setPollOptions(getPollOptions());
    if (LOG.isTraceEnabled()) LOG.trace("pollDetails " + pollDetails);
    return pollDetails;
  }

  public static Iterator<Poll> toPollsIterator(Iterator<? extends Details> iter) {
    if (null == iter) return null;
    ArrayList<Poll> polls = new ArrayList<Poll>();
    while (iter.hasNext()) {
      PollDetails dets = (PollDetails) iter.next();
      Poll thisPoll = Poll.fromPollDetails(dets);
      Link self = thisPoll.getLink("self");
      thisPoll.removeLinks();
      thisPoll.add(self);
      polls.add(thisPoll);
    }
    return polls.iterator();
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long pollId) {
    this.nodeId = pollId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Long getStart() {
    return start;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public List<PollOptionDomain> getPollOptions() {
    return pollOptions;
  }

  public void setPollOptions(List<PollOptionDomain> pollOptions) {
    this.pollOptions = pollOptions;
  }

  public Boolean getClosed() {
    return closed;
  }

  public void setClosed(Boolean closed) {
    this.closed = closed;
  }

  /**
   * @return the ownerId
   */
  public Long getOwnerId() {
    return ownerId;
  }

  /**
   * @param newsFeedId the ownerId to set
   */
  public void setOwnerId(Long newsFeedId) {
    this.ownerId = newsFeedId;
  }

  /**
   * @return the creatorId
   */
  public Long getCreatorId() {
    return creatorId;
  }

  /**
   * @param creatorId the creatorId to set
   */
  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }

  public String getCreatorEmail() {
    return creatorEmail;
  }

  public void setCreatorEmail(String creatorEmail) {
    this.creatorEmail = creatorEmail;
  }
}
