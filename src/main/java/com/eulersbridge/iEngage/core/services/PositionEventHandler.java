package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.core.events.positions.*;
import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Position;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;
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

public class PositionEventHandler implements PositionService {
  private static Logger LOG = LoggerFactory.getLogger(PositionEventHandler.class);

  private PositionRepository positionRepository;
  private ElectionRepository electionRepository;
  private CandidateRepository candidateRepository;

  public PositionEventHandler(PositionRepository positionRepository,
                              ElectionRepository electionRepository, CandidateRepository candidateRepository) {
    this.positionRepository = positionRepository;
    this.electionRepository = electionRepository;
    this.candidateRepository = candidateRepository;
  }

  @Override
  public CreatedEvent createPosition(CreatePositionEvent createPositionEvent) {
    PositionDetails positionDetails = (PositionDetails) createPositionEvent.getDetails();
    Position position = Position.fromPositionDetails(positionDetails);

    if (LOG.isDebugEnabled())
      LOG.debug("Finding election with nodeId = " + positionDetails.getElectionId());
    Election elect = electionRepository.findOne(positionDetails.getElectionId());

    PositionCreatedEvent positionCreatedEvent;
    if (elect != null) {
      position.setElection(elect.toNode());
      Position result = positionRepository.save(position);
      positionCreatedEvent = new PositionCreatedEvent(result.toPositionDetails());
    } else {
      positionCreatedEvent = PositionCreatedEvent.electionNotFound(positionDetails.getElectionId());
    }
    return positionCreatedEvent;
  }

  @Override
  public ReadEvent readPosition(RequestReadPositionEvent requestReadPositionEvent) {
    Position position = positionRepository.findOne(requestReadPositionEvent.getNodeId());
    ReadEvent readPositionEvent;
    if (position != null) {
      readPositionEvent = new PositionReadEvent(position.getNodeId(), position.toPositionDetails());
    } else {
      readPositionEvent = PositionReadEvent.notFound(requestReadPositionEvent.getNodeId());
    }
    return readPositionEvent;
  }

  @Override
  public AllReadEvent readPositions(ReadAllEvent evt, Direction sortDirection, int pageNumber, int pageLength) {
    Long electionId = evt.getParentId();
    Page<Position> positions = null;
    ArrayList<PositionDetails> dets = new ArrayList<PositionDetails>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("ElectionId " + electionId);
    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "e.name");
    positions = positionRepository.findByElectionId(electionId, pageable);
    if (positions != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + positions.getTotalElements() + " total pages =" + positions.getTotalPages());
      Iterator<Position> iter = positions.iterator();
      while (iter.hasNext()) {
        Position na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getName());
        PositionDetails det = na.toPositionDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        Election elec = electionRepository.findOne(electionId);
        if ((null == elec) ||
          ((null == elec.getTitle()) || ((null == elec.getStart()) && (null == elec.getEnd()) && (null == elec.getIntroduction())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ElectionId)");
          nare = AllReadEvent.notFound(null);
        } else {
          nare = new AllReadEvent(electionId, dets, positions.getTotalElements(), positions.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(electionId, dets, positions.getTotalElements(), positions.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }

  @Override
  public AllReadEvent readCandidates(ReadAllEvent readAllEvent,
                                     Direction sortDirection, int pageNumber, int pageLength) {
    Long positionId = readAllEvent.getParentId();
    Page<Candidate> candidates = null;
    ArrayList<CandidateDetails> dets = new ArrayList<CandidateDetails>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("PositionId " + positionId);
    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "e.name");
    candidates = candidateRepository.findByPositionId(positionId, pageable);
    if (candidates != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + candidates.getTotalElements() + " total pages =" + candidates.getTotalPages());
      Iterator<Candidate> iter = candidates.iterator();
      while (iter.hasNext()) {
        Candidate na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getNodeId());
        CandidateDetails det = na.toCandidateDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found instId.
        Position position = positionRepository.findOne(positionId);
        if ((null == position) ||
          ((null == position.getName()) || (null == position.getDescription()))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ElectionId)");
          nare = AllReadEvent.notFound(null);
        } else {
          nare = new AllReadEvent(positionId, dets, candidates.getTotalElements(), candidates.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(positionId, dets, candidates.getTotalElements(), candidates.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }

  @Override
  public UpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent) {
    UpdatedEvent response;
    if (updatePositionEvent != null) {
      PositionDetails positionDetails = (PositionDetails) updatePositionEvent.getDetails();
      Position position = Position.fromPositionDetails(positionDetails);
      Long positionId = positionDetails.getNodeId();
      if (LOG.isDebugEnabled()) LOG.debug("positionId is " + positionId);
      Position positionOld = positionRepository.findOne(positionId, 0);
      if (null == positionOld) {
        if (LOG.isDebugEnabled())
          LOG.debug("position entity not found " + positionId);
        response = PositionUpdatedEvent.notFound(positionId);
      } else {
        Position result = positionRepository.save(position, 0);
        if (result != null) {
          if (LOG.isDebugEnabled())
            LOG.debug("updated successfully" + result.getNodeId());
          response = new PositionUpdatedEvent(result.getNodeId(), result.toPositionDetails());
        } else {
          response = UpdatedEvent.failed(positionId);
        }
      }
    } else {
      response = UpdatedEvent.notFound(null);
    }
    return response;
  }

  @Override
  public DeletedEvent deletePosition(DeletePositionEvent deletePositionEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deletePositionEvent= " + deletePositionEvent);
    Long positionId = deletePositionEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("deletePosition(" + positionId + ")");
    Position position = positionRepository.findOne(positionId);
    if (position == null) {
      return PositionDeletedEvent.notFound(positionId);
    } else {
      positionRepository.delete(positionId);
      PositionDeletedEvent positionDeletedEvent = new PositionDeletedEvent(positionId);
      return positionDeletedEvent;
    }
  }
}
