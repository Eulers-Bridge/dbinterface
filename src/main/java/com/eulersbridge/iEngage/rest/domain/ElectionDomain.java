package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
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

public class ElectionDomain extends ResourceSupport {
  private static Logger LOG = LoggerFactory.getLogger(ElectionDomain.class);

  private Long electionId;
  private String title;
  private Long start;
  private Long end;
  private Long startVoting;
  private Long endVoting;
  private String introduction;
  private String process;

  private InstitutionDomain institutionDomain;
  private List<Position> positionList;

  public static ElectionDomain fromElectionDetails(ElectionDetails electionDetails) {
    ElectionDomain electionDomain = new ElectionDomain();
    electionDomain.setElectionId(electionDetails.getElectionId());
    electionDomain.setTitle(electionDetails.getTitle());
    electionDomain.setStart(electionDetails.getStart());
    electionDomain.setEnd(electionDetails.getEnd());
    electionDomain.setStartVoting(electionDetails.getStartVoting());
    electionDomain.setEndVoting(electionDetails.getEndVoting());
    electionDomain.setIntroduction(electionDetails.getIntroduction());
    electionDomain.setProcess(electionDetails.getProcess());
    return electionDomain;
  }

  public ElectionDetails toElectionDetails() {
    ElectionDetails electionDetails = new ElectionDetails();
    electionDetails.setElectionId(this.getElectionId());
    electionDetails.setTitle(this.getTitle());
    electionDetails.setStart(this.getStart());
    electionDetails.setEnd(this.getEnd());
    electionDetails.setStartVoting(this.getStartVoting());
    electionDetails.setEndVoting(this.getEndVoting());
    electionDetails.setIntroduction(this.getIntroduction());
    electionDetails.setProcess(this.getProcess());
    if (LOG.isTraceEnabled()) LOG.trace("electionDetails " + electionDetails);
    return electionDetails;
  }

  public Long getElectionId() {
    return electionId;
  }

  public void setElectionId(Long electionId) {
    this.electionId = electionId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getStart() {
    return start;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public Long getEnd() {
    return end;
  }

  public void setEnd(Long end) {
    this.end = end;
  }

  public Long getStartVoting() {
    return startVoting;
  }

  public void setStartVoting(Long startVoting) {
    this.startVoting = startVoting;
  }

  public Long getEndVoting() {
    return endVoting;
  }

  public void setEndVoting(Long endVoting) {
    this.endVoting = endVoting;
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

  public InstitutionDomain getInstitutionDomain() {
    return institutionDomain;
  }

  public void setInstitutionDomain(InstitutionDomain institutionDomain) {
    this.institutionDomain = institutionDomain;
  }

  public List<Position> getPositionList() {
    return positionList;
  }

  public void setPositionList(List<Position> positionList) {
    this.positionList = positionList;
  }

  public static Iterator<ElectionDomain> toElectionsIterator(Iterator<? extends Details> iter) {
    if (null == iter) return null;
    ArrayList<ElectionDomain> electionDomains = new ArrayList<ElectionDomain>();
    while (iter.hasNext()) {
      ElectionDetails dets = (ElectionDetails) iter.next();
      ElectionDomain thisElectionDomain = ElectionDomain.fromElectionDetails(dets);
      Link self = thisElectionDomain.getLink("self");
      thisElectionDomain.removeLinks();
      thisElectionDomain.add(self);
      electionDomains.add(thisElectionDomain);
    }
    return electionDomains.iterator();
  }
}
