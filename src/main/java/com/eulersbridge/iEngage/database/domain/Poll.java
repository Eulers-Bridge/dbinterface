package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import com.eulersbridge.iEngage.rest.domain.PollOptionDomain;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Poll extends Likeable implements Commentable {
  private static final Logger LOG = LoggerFactory.getLogger(Poll.class);

  private String question;
  @NotNull
  private Long start;
  @NotNull
  private Long duration;
  @NotNull
  @Index
  private Long end;
  private String image;

  @Index
  private Boolean closed = false;

  @Relationship(type = DataConstants.CREATED_BY_LABEL)
  private Node creator;
  @Relationship(type = DataConstants.HAS_POLL_LABEL, direction = Relationship.OUTGOING)
  private Node institution;
  @Relationship(type = DataConstants.HAS_COMMENT, direction = Relationship.UNDIRECTED)
  private List<Node> comments;
  @Relationship(type = DataConstants.HAS_POLL_OPTION_LABEL, direction = Relationship.OUTGOING)
  private List<Node> pollOptionsList;


  public Poll() {
  }

  public Poll(Long nodeId) {
    super(nodeId);
  }

  public Poll(String question, Long start, Long duration) {
    super();
    this.question = question;
    this.start = start;
    this.duration = duration;
  }

  public PollDetails toPollDetails() {
    PollDetails pollDetails = new PollDetails();
    pollDetails.setPollId(this.getNodeId());
    pollDetails.setQuestion(this.getQuestion());
    pollDetails.setStart(this.getStart());
    pollDetails.setDuration(this.getDuration());
    pollDetails.setImage(this.getImage());
    pollDetails.setClosed(this.getClosed());

    if (pollOptionsList != null) {
      List<PollOptionDomain> pollOptionDomains = getPollOptionsList$()
        .stream()
        .map(pollOption -> pollOption.toDomainObj())
        .collect(Collectors.toList());
      pollDetails.setPollOptions(pollOptionDomains);
    }
    if (institution != null)
      pollDetails.setOwnerId(institution.getNodeId());
    else
      pollDetails.setCreatorId(null);

    if (creator != null) {
      pollDetails.setCreatorId(creator.getNodeId());
      if (creator instanceof User)
        pollDetails.setCreatorEmail(getCreator$().getEmail());
    }

    pollDetails.setNumOfComments(getNumberOfComments());
    return pollDetails;
  }

  public static Poll fromPollDetails(PollDetails pollDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromPollDetails()");
    Poll poll = new Poll();
    poll.setNodeId(pollDetails.getPollId());
    poll.setQuestion(pollDetails.getQuestion());
    poll.setStart(pollDetails.getStart());
    poll.setDuration(pollDetails.getDuration());
    if (pollDetails.getStart() != null && pollDetails.getDuration() != null)
      poll.setEnd(pollDetails.getStart() + pollDetails.getDuration());

    poll.setImage(pollDetails.getImage());
    Institution owner = new Institution();
    owner.setNodeId(pollDetails.getOwnerId());
    poll.setInstitution(owner.toNode());
    User creator = new User(pollDetails.getCreatorId());
    poll.setCreator(creator.toNode());

    if (pollDetails.getPollOptions() != null) {
      List<Node> pollOptions = pollDetails.getPollOptions().stream()
        .map(PollOption::fromPollOptionDomain)
        .collect(Collectors.toList());
      poll.setPollOptionsList(pollOptions);
    }
    if (LOG.isTraceEnabled()) LOG.trace("poll " + poll);
    return poll;
  }


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
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

  public List<Comment> getComments$() {
    return castList(comments, Comment.class);
  }

  public List<Node> getComments() {
    return comments;
  }

  public void setComments(List<Node> comments) {
    this.comments = comments;
  }


  public List<Node> getPollOptionsList() {
    return pollOptionsList;
  }

  public List<PollOption> getPollOptionsList$() {
    return castList(pollOptionsList, PollOption.class);
  }

  public void setPollOptionsList(List<Node> pollOptionsList) {
    this.pollOptionsList = pollOptionsList;
  }


  public Integer getNumberOfComments() {
    if (comments == null)
      return 0;
    return comments.size();
  }


  public User getCreator$() {
    return (User) creator;
  }

  public Node getCreator() {
    return creator;
  }

  public void setCreator(Node creator) {
    this.creator = creator;
  }

  public Institution getInstitution$() {
    return (Institution) institution;
  }

  public Node getInstitution() {
    return institution;
  }

  public void setInstitution(Node institution) {
    this.institution = institution;
  }

  public Long getEnd() {
    return end;
  }

  public void setEnd(Long end) {
    this.end = end;
  }

  public Boolean getClosed() {
    return closed;
  }

  public void setClosed(Boolean closed) {
    this.closed = closed;
  }

  @Override
  public String toString() {
    return "Poll [nodeId=" + nodeId + ", question=" + question
      + ", start=" + start + ", duration="
      + duration + ", creator=" + creator + ", institution=" + institution + "]";
  }

}
