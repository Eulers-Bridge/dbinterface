package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */

public class ElectionEventHandler implements ElectionService {

  private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandler.class);

  private ElectionRepository eleRepository;
  private InstitutionRepository instRepository;

  public ElectionEventHandler(ElectionRepository electionRepository, InstitutionRepository instRepo) {
    this.eleRepository = electionRepository;
    this.instRepository = instRepo;
  }

  @Override
  public ReadEvent readElection(RequestReadElectionEvent requestReadElectionEvent) {
    Election election = eleRepository.findOne(requestReadElectionEvent.getNodeId());
    ReadEvent readElectionEvent;
    if (election != null) {
      readElectionEvent = new ReadElectionEvent(election.getNodeId(), election.toElectionDetails());
    } else {
      readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getNodeId());
    }
    return readElectionEvent;
  }

  @Override
  public ElectionCreatedEvent createElection(CreateElectionEvent createElectionEvent) {
    ElectionDetails electionDetails = (ElectionDetails) createElectionEvent.getDetails();
    Election election = Election.fromElectionDetails(electionDetails);

    if (LOG.isDebugEnabled())
      LOG.debug("Finding institution with instId = " + electionDetails.getInstitutionId());
    Institution inst = instRepository.findOne(electionDetails.getInstitutionId(), 0);
    ElectionCreatedEvent electionCreatedEvent;
    if (inst != null) {
      election.setInstitution(inst.toNode());
      Election result = eleRepository.save(election);
      electionCreatedEvent = new ElectionCreatedEvent(result.getNodeId(), result.toElectionDetails());
    } else {
      electionCreatedEvent = ElectionCreatedEvent.institutionNotFound(electionDetails.getInstitutionId());
    }
    return electionCreatedEvent;
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
  public AllReadEvent readElections(ReadAllEvent readElectionsEvent, Direction sortDirection, int pageNumber, int pageLength) {
    Long institutionId = readElectionsEvent.getParentId();
    Page<Election> elections = null;
    ArrayList<ElectionDetails> dets = new ArrayList<ElectionDetails>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("InstitutionId " + institutionId);
    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "e.start");
    elections = eleRepository.findByInstitutionId(institutionId, pageable);
    if (elections != null) {
      Long numElements = elections.getTotalElements();
      Integer numPages = elections.getTotalPages();
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + numElements + " total pages =" + numPages);
      Iterator<Election> iter = elections.iterator();
      while (iter.hasNext()) {
        Election na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getTitle());
        ElectionDetails det = na.toElectionDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found instId.
        Institution inst = instRepository.findOne(institutionId);
        if ((null == inst) ||
          ((null == inst.getName()) || ((null == inst.getCampus()) && (null == inst.getState()) && (null == inst.getCountry())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(InstitutionId)");
          nare = AllReadEvent.notFound(null);
        } else {
          nare = new AllReadEvent(institutionId, dets, numElements, numPages);
        }
      } else {
        nare = new AllReadEvent(institutionId, dets, numElements, numPages);
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }
}
