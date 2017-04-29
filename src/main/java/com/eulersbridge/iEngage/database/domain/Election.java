package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NodeEntity
public class Election extends Likeable {
  private String title;
  private Long start;
  private Long end;
  private Long votingStart;
  private Long votingEnd;
  @Relationship(type = DatabaseDomainConstants.HAS_ELECTION_LABEL, direction = Relationship.OUTGOING)
  private Node institution;
  private String introduction;
  private String process;

  private static Logger LOG = LoggerFactory.getLogger(Election.class);

  public Election() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public Election(Long nodeId, String title, Long start, Long end, Long votingStart,
                  Long votingEnd, Institution inst, String introduction, String process) {
    if (LOG.isDebugEnabled())
      LOG.debug("Constructor(" + nodeId + ',' + start + ',' + end + ',' + votingStart + ',' + votingEnd + ',' + introduction + ',' + process + ')');
    this.nodeId = nodeId;
    this.title = title;
    this.start = start;
    this.end = end;
    this.votingStart = votingStart;
    this.votingEnd = votingEnd;
    this.institution = inst;
    this.introduction = introduction;
    this.process = process;
  }

  public Long getStart() {
    if (LOG.isDebugEnabled()) LOG.debug("getStart() = " + start);
    return start;
  }

  public Long getEnd() {
    if (LOG.isDebugEnabled()) LOG.debug("getEnd() = " + end);
    return end;
  }

  Long getVotingStart() {
    if (LOG.isDebugEnabled()) LOG.debug("getVotingStart() = " + votingStart);
    return votingStart;
  }

  public String getTitle() {
    if (LOG.isDebugEnabled()) LOG.debug("getTitle() = " + title);
    return this.title;
  }

  Long getVotingEnd() {
    if (LOG.isDebugEnabled()) LOG.debug("getVotingEnd() = " + votingEnd);
    return votingEnd;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public void setEnd(Long end) {
    this.end = end;
  }

  public void setVotingStart(Long votingStart) {
    this.votingStart = votingStart;
  }

  public void setVotingEnd(Long votingEnd) {
    this.votingEnd = votingEnd;
  }

  public void setTitle(String title) {
    this.title = title;
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

  /**
   * @return the introduction
   */
  public String getIntroduction() {
    return introduction;
  }

  /**
   * @param introduction the introduction to set
   */
  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  /**
   * @return the process
   */
  public String getProcess() {
    return process;
  }

  /**
   * @param process the process to set
   */
  public void setProcess(String process) {
    this.process = process;
  }

  public String toString() {
    String buff = "[ nodeId = " + getNodeId() +
      ", title = " + getTitle() +
      ", start = " + getStart() +
      ", end = " + getEnd() +
      " , voting start = " + getVotingStart() +
      ", voting end = " + getVotingEnd() +
      ", institution = " + getInstitution() +
      ", introduction = " + getIntroduction() +
      ", process = " + getProcess() +
      " ]";
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + buff);
    return buff;
  }

  public ElectionDetails toElectionDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toElectionDetails()");

    ElectionDetails electionDetails = new ElectionDetails();
    if (LOG.isTraceEnabled()) LOG.trace("election " + this);
    electionDetails.setElectionId(this.getNodeId());
    electionDetails.setTitle(this.getTitle());
    electionDetails.setStart(this.getStart());
    electionDetails.setEnd(this.getEnd());
    electionDetails.setStartVoting(this.getVotingStart());
    electionDetails.setEndVoting(this.getVotingEnd());
    if (getInstitution() != null)
      electionDetails.setInstitutionId(this.getInstitution().getNodeId());
    else electionDetails.setInstitutionId(null);
    electionDetails.setIntroduction(getIntroduction());
    electionDetails.setProcess(this.getProcess());
    if (LOG.isTraceEnabled()) LOG.trace("electionDetail; " + electionDetails);
    return electionDetails;
  }

  public static Election fromElectionDetails(ElectionDetails electionDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromElectionDetails()");
    Election election = new Election();
    if (LOG.isTraceEnabled()) LOG.trace("electionDetails " + electionDetails);
    election.setNodeId(electionDetails.getElectionId());
    election.setTitle(electionDetails.getTitle());
    election.setStart(electionDetails.getStart());
    election.setEnd(electionDetails.getEnd());
    election.setVotingStart(electionDetails.getStartVoting());
    election.setVotingEnd(electionDetails.getEndVoting());
    Institution inst = new Institution();
    inst.setNodeId(electionDetails.getInstitutionId());
    election.setInstitution(inst);
    election.setIntroduction(electionDetails.getIntroduction());
    election.setProcess(electionDetails.getProcess());
    if (LOG.isTraceEnabled()) LOG.trace("election " + election);
    return election;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (this.nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result + ((end == null) ? 0 : end.hashCode());
      result = prime * result
        + ((institution == null) ? 0 : institution.hashCode());
      result = prime * result + ((start == null) ? 0 : start.hashCode());
      result = prime * result + ((title == null) ? 0 : title.hashCode());
      result = prime * result + ((introduction == null) ? 0 : introduction.hashCode());
      result = prime * result + ((process == null) ? 0 : process.hashCode());
      result = prime * result
        + ((votingEnd == null) ? 0 : votingEnd.hashCode());
      result = prime * result
        + ((votingStart == null) ? 0 : votingStart.hashCode());
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
    Election other = (Election) obj;

    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;
      if (end == null) {
        if (other.end != null)
          return false;
      } else if (!end.equals(other.end))
        return false;
      if (institution == null) {
        if (other.institution != null)
          return false;
      } else if (!institution.equals(other.institution))
        return false;
      if (start == null) {
        if (other.start != null)
          return false;
      } else if (!start.equals(other.start))
        return false;
      if (title == null) {
        if (other.title != null)
          return false;
      } else if (!title.equals(other.title))
        return false;
      if (introduction == null) {
        if (other.introduction != null)
          return false;
      } else if (!introduction.equals(other.introduction))
        return false;
      if (process == null) {
        if (other.process != null)
          return false;
      } else if (!process.equals(other.process))
        return false;
      if (votingEnd == null) {
        if (other.votingEnd != null)
          return false;
      } else if (!votingEnd.equals(other.votingEnd))
        return false;
      if (votingStart == null) {
        if (other.votingStart != null)
          return false;
      } else if (!votingStart.equals(other.votingStart))
        return false;
    }
    return true;
  }
}
