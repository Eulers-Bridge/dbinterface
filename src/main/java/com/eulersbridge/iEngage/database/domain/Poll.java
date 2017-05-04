package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Poll extends Likeable implements Commentable {
  private String question;
  private String answers;
  private Long start;
  private Long duration;
  @Relationship(type = DatabaseDomainConstants.CREATED_BY_LABEL)
  private Node creator;
  @Relationship(type = DatabaseDomainConstants.HAS_POLL_LABEL)
  private Node institution;
  @Relationship(type = DatabaseDomainConstants.HAS_COMMENT, direction = Relationship.UNDIRECTED)
  private List<Node> comments;
  @Relationship(type = DatabaseDomainConstants.APQ_LABEL, direction = Relationship.UNDIRECTED)
  private List<Node> answeredUsers;

  private static Logger LOG = LoggerFactory.getLogger(Poll.class);

  public Poll() {
  }

  public Poll(String question, String answers, Long start, Long duration) {
    super();
    this.question = question;
    this.answers = answers;
    this.start = start;
    this.duration = duration;
  }

  public PollDetails toPollDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toPollDetails()");
    PollDetails pollDetails = new PollDetails();
    pollDetails.setPollId(this.getNodeId());
    pollDetails.setQuestion(this.getQuestion());
    pollDetails.setAnswers(this.getAnswers());
    pollDetails.setStart(this.getStart());
    pollDetails.setDuration(this.getDuration());
    pollDetails.setOwnerId((institution == null) ? null : institution.getNodeId());
    pollDetails.setCreatorId((creator == null) ? null : creator.getNodeId());
    pollDetails.setCreatorEmail((creator == null) ? null : getCreator().getEmail());
    pollDetails.setNumOfComments(getNumberOfComments());
    pollDetails.setNumOfAnswers(getNumberOfAnswers());
    if (LOG.isTraceEnabled()) LOG.trace("pollDetails; " + pollDetails);
    return pollDetails;
  }

  public static Poll fromPollDetails(PollDetails pollDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromPollDetails()");
    Poll poll = new Poll();
    poll.setNodeId(pollDetails.getPollId());
    poll.setQuestion(pollDetails.getQuestion());
    poll.setAnswers(pollDetails.getAnswers());
    poll.setStart(pollDetails.getStart());
    poll.setDuration(pollDetails.getDuration());
//		Owner institution = new Owner(pollDetails.getOwnerId());
    Institution owner = new Institution();
    owner.setNodeId(pollDetails.getOwnerId());
    poll.setInstitution(owner);
    User creator = new User(pollDetails.getCreatorId());
    poll.setCreator(creator);
    if (LOG.isTraceEnabled()) LOG.trace("poll " + poll);
    return poll;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswers() {
    return answers;
  }

  public void setAnswers(String answers) {
    this.answers = answers;
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

  public List<Comment> getComments() {
    return castList(comments, Comment.class);
  }

  public void setComments(List<Node> comments) {
    this.comments = comments;
  }

  public List<User> getAnsweredUsers() {
    return castList(answeredUsers, User.class);
  }

  public void setAnsweredUsers(List<Node> answeredUsers) {
    this.answeredUsers = answeredUsers;
  }

  public Integer getNumberOfComments() {
    if (comments == null)
      return 0;
    return comments.size();
  }

  public Integer getNumberOfAnswers() {
    if (answeredUsers == null)
      return 0;
    return answeredUsers.size();
  }


  /**
   * @return the creator
   */
  public User getCreator() {
    return (User) creator;
  }

  /**
   * @param creator the creator to set
   */
  public void setCreator(Node creator) {
    this.creator = creator;
  }

  /**
   * @return the institution
   */
  public Institution getInstitution() {
    return (Institution) institution;
  }

  /**
   * @param institution the institution to set
   */
  public void setInstitution(Node institution) {
    this.institution = institution;
  }

  /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
  @Override
  public String toString() {
    return "Poll [nodeId=" + nodeId + ", question=" + question
      + ", answers=" + answers + ", start=" + start + ", duration="
      + duration + ", creator=" + creator + ", institution=" + institution + "]";
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
    if (nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result
        + ((answers == null) ? 0 : answers.hashCode());
      result = prime * result
        + ((creator == null) ? 0 : creator.hashCode());
      result = prime * result + ((institution == null) ? 0 : institution.hashCode());
      result = prime * result
        + ((duration == null) ? 0 : duration.hashCode());
      result = prime * result
        + ((question == null) ? 0 : question.hashCode());
      result = prime * result + ((start == null) ? 0 : start.hashCode());
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
    Poll other = (Poll) obj;
    if (nodeId != null) {
      if (nodeId.equals(other.nodeId))
        return true;
      else return false;
    } else {
      if (other.nodeId != null) return false;
      if (answers == null) {
        if (other.answers != null) return false;
      } else if (!answers.equals(other.answers)) return false;
      if (institution == null) {
        if (other.institution != null) return false;
      } else if (!institution.equals(other.institution)) return false;
      if (creator == null) {
        if (other.creator != null) return false;
      } else if (!creator.equals(other.creator)) return false;
      if (duration == null) {
        if (other.duration != null) return false;
      } else if (!duration.equals(other.duration)) return false;
      if (question == null) {
        if (other.question != null) return false;
      } else if (!question.equals(other.question)) return false;
      if (start == null) {
        if (other.start != null) return false;
      } else if (!start.equals(other.start)) return false;
    }
    return true;
  }

}
