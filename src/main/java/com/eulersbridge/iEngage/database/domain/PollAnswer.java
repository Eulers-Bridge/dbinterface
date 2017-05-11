package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RelationshipEntity(type = DataConstants.APQ_LABEL)
public class PollAnswer {
  private static final Logger LOG = LoggerFactory.getLogger(PollAnswer.class);

  @GraphId
  private Long id;
  @StartNode
  private Owner user;
  @EndNode
  private Poll poll;
  Integer answerIndex;
  private Long timeStamp;

  public PollAnswer() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public PollAnswer(Owner answerer, Poll poll, Integer answer) {
    this.user = answerer;
    this.poll = poll;
    this.answerIndex = answer;
  }

  /**
   * @return the id
   */
  public Long getNodeId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setNodeId(Long id) {
    this.id = id;
  }

  /**
   * @return the user
   */
  public Owner getUser() {
    return user;
  }

  /**
   * @param answerer the user to set
   */
  public void setUser(Owner answerer) {
    this.user = answerer;
  }

  /**
   * @return the poll
   */
  public Poll getPoll() {
    return poll;
  }

  /**
   * @param poll the poll to set
   */
  public void setPoll(Poll poll) {
    this.poll = poll;
  }

  /**
   * @return the timeStamp
   */
  public Long getTimeStamp() {
    return timeStamp;
  }

  /**
   * @param timeStamp the timeStamp to set
   */
  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }

  /**
   * @return the answer
   */
  public Integer getAnswer() {
    return answerIndex;
  }

  /**
   * @param answer the answer to set
   */
  public void setAnswer(Integer answer) {
    this.answerIndex = answer;
  }

  public PollAnswerDetails toPollAnswerDetails() {
    Long userId = null, pollId = null;
    if (user != null) userId = user.getNodeId();
    if (poll != null) pollId = poll.getNodeId();
    PollAnswerDetails dets = new PollAnswerDetails(id, userId, pollId, answerIndex, timeStamp);
    return dets;
  }

  static public PollAnswer fromPollAnswerDetails(PollAnswerDetails dets) {
    PollAnswer answer = new PollAnswer();
    answer.setAnswer(dets.getAnswerIndex());
    Owner answerer = new Owner();
    answerer.setNodeId(dets.getAnswererId());
    answer.setUser(answerer);
    Poll poll = new Poll();
    poll.setNodeId(dets.getPollId());
    answer.setPoll(poll);
    answer.setNodeId(dets.getNodeId());
    if (dets.getTimeStamp() != null) answer.setTimeStamp(dets.getTimeStamp());
    return answer;
  }

  @Override
  public String toString() {
    return "id = " + id + " user = " + user + " poll = " + poll + " timeStamp = " + timeStamp + " answerIndex = " + answerIndex;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (id != null) {
      result = prime * result + id.hashCode();
    } else {
      result = prime * result + ((answerIndex == null) ? 0 : answerIndex.hashCode());
      result = prime * result
        + ((user == null) ? 0 : user.hashCode());
      result = prime * result + ((poll == null) ? 0 : poll.hashCode());
      result = prime * result
        + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
    PollAnswer other = (PollAnswer) obj;
    if (id != null) {
      if (id.equals(other.id))
        return true;
      else return false;
    } else {
      if (other.id != null)
        return false;
      if (answerIndex == null) {
        if (other.answerIndex != null)
          return false;
      } else if (!answerIndex.equals(other.answerIndex))
        return false;
      if (user == null) {
        if (other.user != null)
          return false;
      } else if (!user.equals(other.user))
        return false;
      if (poll == null) {
        if (other.poll != null)
          return false;
      } else if (!poll.equals(other.poll))
        return false;
      if (timeStamp == null) {
        if (other.timeStamp != null)
          return false;
      } else if (!timeStamp.equals(other.timeStamp))
        return false;
    }
    return true;
  }

}
