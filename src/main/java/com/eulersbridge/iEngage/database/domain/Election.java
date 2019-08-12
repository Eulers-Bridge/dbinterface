package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.rest.domain.ElectionDomain;
import com.eulersbridge.iEngage.rest.domain.InstitutionDomain;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NodeEntity
public class Election extends Likeable {
  private static final Logger LOG = LoggerFactory.getLogger(Election.class);

  private String title;
  private Long start;
  private Long end;
  private Long votingStart;
  private Long votingEnd;
  private String introduction;
  private String process;

  @Relationship(type = DataConstants.HAS_ELECTION_LABEL, direction = Relationship.OUTGOING)
  private Node institution;

  public Election() {
  }

  public Election(Long nodeId, String title, Long start, Long end, Long votingStart,
                  Long votingEnd, Institution inst, String introduction, String process) {
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
    return start;
  }

  public Long getEnd() {
    return end;
  }

  public Long getVotingStart() {
    return votingStart;
  }

  public String getTitle() {
    return this.title;
  }

  public Long getVotingEnd() {
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

  public Institution getInstitution$() {
    return new Institution(institution.nodeId);
  }

  public Node getInstitution() {
    return institution;
  }

  public void setInstitution(Node institution) {
    this.institution = institution;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public String getProcess() {
    return process;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  public ElectionDetails toElectionDetails() {
    ElectionDetails electionDetails = new ElectionDetails();
    electionDetails.setElectionId(getNodeId());
    electionDetails.setTitle(getTitle());
    electionDetails.setStart(getStart());
    electionDetails.setEnd(getEnd());
    electionDetails.setStartVoting(getVotingStart());
    electionDetails.setEndVoting(getVotingEnd());
    electionDetails.setIntroduction(getIntroduction());
    electionDetails.setProcess(getProcess());

    if (institution != null)
      electionDetails.setInstitutionId(institution.getNodeId());
    else
      electionDetails.setInstitutionId(null);

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
    election.setInstitution(inst.toNode());
    election.setIntroduction(electionDetails.getIntroduction());
    election.setProcess(electionDetails.getProcess());
    if (LOG.isTraceEnabled()) LOG.trace("election " + election);
    return election;
  }

  public static Election fromDomain(ElectionDomain electionDomain) {
    Election election = new Election();
    election.setNodeId(electionDomain.getElectionId());
    election.setEnd(electionDomain.getEnd());
    election.setVotingEnd(electionDomain.getEndVoting());
    election.setProcess(electionDomain.getProcess());
    election.setStart(electionDomain.getStart());
    election.setVotingStart(electionDomain.getStartVoting());
    election.setTitle(electionDomain.getTitle());

    return election;
  }

  public ElectionDomain toDomain() {
    ElectionDomain domain = new ElectionDomain();
    domain.setElectionId(nodeId);
    domain.setTitle(title);
    domain.setEnd(end);
    domain.setStart(start);
    domain.setStartVoting(votingStart);
    domain.setEndVoting(votingEnd);
    domain.setProcess(process);

    if (getInstitution() != null)
      domain.setInstitutionDomain(new InstitutionDomain(getInstitution().nodeId));
    return domain;
  }

  public boolean isValidForCreation() {
    if (nodeId != null)
      return false;
    return isValid();
  }

  public boolean isValidForUpdate() {
    if (nodeId == null)
      return false;
    return isValid();
  }

  public boolean isValid() {
    if (title == null || start == null || end == null || votingEnd == null || votingStart == null)
      return false;
    return true;
  }
}
