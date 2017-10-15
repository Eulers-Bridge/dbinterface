package com.eulersbridge.iEngage.core.events.polls;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.rest.domain.PollOptionDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Yikai Gong
 */

public class PollDetails extends Details {
  private String question;
  private List<PollOptionDomain> pollOptions;
  private Long start;
  private Long duration;
  private Long ownerId;
  private Long creatorId;
  private String creatorEmail;
  private String image;

  private Integer numOfComments;
  private Integer numOfAnswers;

  private static Logger LOG = LoggerFactory.getLogger(PollDetails.class);

  public PollDetails() {
    super();
  }

  public Long getPollId() {
    return nodeId;
  }

  public void setPollId(Long pollId) {
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

  public Integer getNumOfComments() {
    return numOfComments;
  }

  public void setNumOfComments(Integer numOfComments) {
    this.numOfComments = numOfComments;
  }

  /**
   * @return the numOfAnswers
   */
  public Integer getNumOfAnswers() {
    return numOfAnswers;
  }

  /**
   * @param numOfAnswers the numOfAnswers to set
   */
  public void setNumOfAnswers(Integer numOfAnswers) {
    this.numOfAnswers = numOfAnswers;
  }

  /**
   * @return the ownerId
   */
  public Long getOwnerId() {
    return ownerId;
  }

  /**
   * @param ownerId the ownerId to set
   */
  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
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

  public static Logger getLOG() {
    return LOG;
  }

  public static void setLOG(Logger LOG) {
    PollDetails.LOG = LOG;
  }

  @Override
  public String toString() {
    StringBuffer buff = new StringBuffer("[ id = ");
    String retValue;
    buff.append(getPollId());
    buff.append(", question = ");
    buff.append(getQuestion());
    buff.append(", answers = ");
    buff.append(", start = ");
    buff.append(getStart());
    buff.append(", duration = ");
    buff.append(getDuration());
    buff.append(", ownerId = ");
    buff.append(getOwnerId());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
      result = prime * result + ((creatorId == null) ? 0 : creatorId.hashCode());
      result = prime * result
        + ((duration == null) ? 0 : duration.hashCode());
      result = prime * result
        + ((question == null) ? 0 : question.hashCode());
      result = prime * result + ((start == null) ? 0 : start.hashCode());
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
    PollDetails other = (PollDetails) obj;
    if (nodeId != null) {
      if (nodeId.equals(other.nodeId))
        return true;
      else return false;
    } else {
      if (other.nodeId != null)
        return false;
      if (duration == null) {
        if (other.duration != null)
          return false;
      } else if (!duration.equals(other.duration))
        return false;
      if (question == null) {
        if (other.question != null)
          return false;
      } else if (!question.equals(other.question))
        return false;
      if (ownerId == null) {
        if (other.ownerId != null)
          return false;
      } else if (!ownerId.equals(other.ownerId))
        return false;
      if (creatorId == null) {
        if (other.creatorId != null)
          return false;
      } else if (!creatorId.equals(other.creatorId))
        return false;
      if (start == null) {
        if (other.start != null)
          return false;
      } else if (!start.equals(other.start))
        return false;
    }
    return true;
  }

}
