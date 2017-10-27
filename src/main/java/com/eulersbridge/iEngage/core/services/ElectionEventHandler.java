package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.core.services.interfacePack.ElectionService;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.rest.domain.ElectionDomain;
import com.eulersbridge.iEngage.rest.domain.InstitutionDomain;
import com.eulersbridge.iEngage.rest.domain.WrappedDomainList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yikai Gong
 */

@Service
public class ElectionEventHandler implements ElectionService {

  private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandler.class);

  private ElectionRepository eleRepository;
  private InstitutionRepository instRepository;

  @Autowired
  public ElectionEventHandler(ElectionRepository electionRepository, InstitutionRepository instRepo) {
    this.eleRepository = electionRepository;
    this.instRepository = instRepo;
  }

  @Override
  public RequestHandledEvent createElection(ElectionDomain electionDomain) {
    Election election = Election.fromDomain(electionDomain);
    InstitutionDomain institutionDomain = electionDomain.getInstitutionDomain();
    if (institutionDomain != null && institutionDomain.getInstitutionId() != null) {
      Institution ins = instRepository.findOne(institutionDomain.getInstitutionId(), 0);
      if (ins == null)
        return RequestHandledEvent.targetNotFound();
      election.setInstitution(ins.toNode());
    }
    if (!election.isValidForCreation())
      return RequestHandledEvent.badRequest();
    election = eleRepository.save(election, 1);
    return new RequestHandledEvent<>(election.toDomain());
  }

  @Override
  public RequestHandledEvent readElection(Long electionId) {
    if (electionId == null)
      return RequestHandledEvent.badRequest();
    Election election = eleRepository.findOne(electionId);
    if (election == null)
      return RequestHandledEvent.targetNotFound();
    return new RequestHandledEvent<>(election.toDomain());
  }

  @Override
  public ReadEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent) {
    Election election = eleRepository.findPreviousElection(requestReadElectionEvent.getNodeId());
    if (LOG.isDebugEnabled()) LOG.debug("election = " + election);
    ReadEvent readElectionEvent;
    if (election != null) {
      readElectionEvent = new ReadElectionEvent(requestReadElectionEvent.getNodeId(), election.toElectionDetails());
    } else {
      readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getNodeId());
    }
    return readElectionEvent;
  }

  @Override
  public ReadEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent) {
    Election election = eleRepository.findNextElection(requestReadElectionEvent.getNodeId());
    if (LOG.isDebugEnabled()) LOG.debug("election = " + election);
    ReadEvent readElectionEvent;
    if (election != null) {
      readElectionEvent = new ReadElectionEvent(requestReadElectionEvent.getNodeId(), election.toElectionDetails());
    } else {
      readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getNodeId());
    }
    return readElectionEvent;
  }

  @Override
  public DeletedEvent deleteElection(DeleteElectionEvent deleteElectionEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteElectionEvent= " + deleteElectionEvent);
    Long electionId = deleteElectionEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("deleteElection(" + electionId + ")");
    Election election = eleRepository.findOne(electionId);
    if (election == null) {
      return ElectionDeletedEvent.notFound(electionId);
    } else {
      eleRepository.delete(electionId);
      ElectionDeletedEvent electionDeletedEvent = new ElectionDeletedEvent(electionId);
      return electionDeletedEvent;
    }
  }

  @Override
  public UpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent) {
    ElectionDetails electionDetails = updateElectionEvent.getElectionDetails();
    Election election = Election.fromElectionDetails(electionDetails);
    Long electionId = electionDetails.getElectionId();
    if (LOG.isDebugEnabled()) LOG.debug("election Id is " + electionId);
    Election electionOld = eleRepository.findOne(electionId);
    if (electionOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("election entity not found " + electionId);
      return ElectionUpdatedEvent.notFound(electionId);
    } else {
      Election result = eleRepository.save(election, 0);
      if (LOG.isDebugEnabled())
        LOG.debug("updated successfully" + result.getNodeId());
      return new ElectionUpdatedEvent(result.getNodeId(), result.toElectionDetails());
    }
  }

  @Override
  public RequestHandledEvent readElections(Long institutionID, int pageIndex, int pageSize) {
    if (institutionID == null || pageIndex < 0 || pageSize <= 0)
      return RequestHandledEvent.badRequest();
    Institution ins = instRepository.findOne(institutionID, 0);
    if (ins == null)
      return RequestHandledEvent.targetNotFound();
    List<ElectionDomain> elections =
      eleRepository.findByInstitutionId(ins.getNodeId()).stream()
        .map(Election::toDomain).collect(Collectors.toList());
    WrappedDomainList<ElectionDomain> wl = new WrappedDomainList<>(elections, elections.size(), 1);
    return new RequestHandledEvent<>(wl);
  }
}
