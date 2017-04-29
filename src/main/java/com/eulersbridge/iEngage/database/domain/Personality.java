package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

@NodeEntity
public class Personality extends Node{

  private Float extroversion;
  private Float agreeableness;
  private Float conscientiousness;
  private Float emotionalStability;
  private Float openess;

  private static Logger LOG = LoggerFactory.getLogger(Personality.class);

  public Personality() {
    super();
  }

  public Personality(Float extroversion, Float agreeableness,
                     Float conscientiousness, Float emotionalStability, Float openess) {
    super();
    this.extroversion = extroversion;
    this.agreeableness = agreeableness;
    this.conscientiousness = conscientiousness;
    this.emotionalStability = emotionalStability;
    this.openess = openess;
  }

  /**
   * @return the extroversion
   */
  public Float getExtroversion() {
    return extroversion;
  }

  /**
   * @return the agreeableness
   */
  public Float getAgreeableness() {
    return agreeableness;
  }

  /**
   * @return the conscientiousness
   */
  public Float getConscientiousness() {
    return conscientiousness;
  }

  /**
   * @return the emotionalStability
   */
  public Float getEmotionalStability() {
    return emotionalStability;
  }

  /**
   * @return the openess
   */
  public Float getOpeness() {
    return openess;
  }

  public PersonalityDetails toPersonalityDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toPersonalityDetails()");

    PersonalityDetails details = new PersonalityDetails();
    if (LOG.isTraceEnabled()) LOG.trace("personality " + this);

    BeanUtils.copyProperties(this, details);
    details.setPersonalityId(getNodeId());
    if (LOG.isTraceEnabled()) LOG.trace("personalityDetails " + details);

    return details;
  }

  public static Personality fromPersonalityDetails(PersonalityDetails dets) {
    if (LOG.isTraceEnabled()) LOG.trace("fromUserDetails()");

    if (LOG.isTraceEnabled()) LOG.trace("personalityDetails " + dets);
    Personality personality = new Personality(dets.getExtroversion(), dets.getAgreeableness(), dets.getConscientiousness(), dets.getEmotionalStability(), dets.getOpeness());
    personality.setNodeId(dets.getPersonalityId());
    if (LOG.isTraceEnabled()) LOG.trace("personality " + personality);

    return personality;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Personality [nodeId=" + nodeId + ", extroversion="
      + extroversion + ", agreeableness=" + agreeableness
      + ", conscientiousness=" + conscientiousness
      + ", emotionalStability=" + emotionalStability + ", openess="
      + openess + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (nodeId != null)
      return nodeId.hashCode();
    else {
      result = prime * result + ((agreeableness == null) ? 0 : agreeableness.hashCode());
      result = prime * result + ((conscientiousness == null) ? 0 : conscientiousness.hashCode());
      result = prime * result + ((emotionalStability == null) ? 0 : emotionalStability.hashCode());
      result = prime * result + ((extroversion == null) ? 0 : extroversion.hashCode());
      result = prime * result + ((openess == null) ? 0 : openess.hashCode());
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
    Personality other = (Personality) obj;
    if (nodeId != null) {
      if (!nodeId.equals(other.getNodeId()))
        return false;
    } else {
      if (other.getNodeId() != null)
        return false;
      else {
        Float delta = 0.001F;
        if ((agreeableness != null) && (other.agreeableness != null)) {
          if (Math.abs(agreeableness - other.agreeableness) > delta)
            return false;
        } else if ((agreeableness != null) || (other.agreeableness != null))
          return false;
        if ((conscientiousness != null) && (other.conscientiousness != null)) {
          if (Math.abs(conscientiousness - other.conscientiousness) > delta)
            return false;
        } else if ((conscientiousness != null) || (other.conscientiousness != null))
          return false;
        if ((emotionalStability != null) && (other.emotionalStability != null)) {
          if (Math.abs(emotionalStability - other.emotionalStability) > delta)
            return false;
        } else if ((emotionalStability != null) || (other.emotionalStability != null))
          return false;

        if ((extroversion != null) && (other.extroversion != null)) {
          if (Math.abs(extroversion - other.extroversion) > delta)
            return false;
        } else if ((extroversion != null) || (other.extroversion != null))
          return false;

        if ((openess != null) && (other.openess != null)) {
          if (Math.abs(openess - other.openess) > delta)
            return false;
        } else if ((openess != null) || (other.openess != null))
          return false;
      }
    }
    return true;
  }

}
