package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.core.services.interfacePack.CandidateService;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;
import com.eulersbridge.iEngage.rest.domain.CandidateDomain;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */

@Service
public class CandidateEventHandler implements CandidateService {
  private static Logger LOG = LoggerFactory.getLogger(CandidateService.class);

  private CandidateRepository candidateRepository;
  private UserRepository userRepository;
  private PositionRepository positionRepository;
  private ElectionRepository electionRepository;
  private TicketRepository ticketRepository;

  @Autowired
  public CandidateEventHandler(CandidateRepository candidateRepository,
                               UserRepository userRepository,
                               PositionRepository positionRepository,
                               ElectionRepository electionRepository,
                               TicketRepository ticketRepository) {
    this.candidateRepository = candidateRepository;
    this.userRepository = userRepository;
    this.positionRepository = positionRepository;
    this.electionRepository = electionRepository;
    this.ticketRepository = ticketRepository;
  }

  @Override
  public RequestHandledEvent<CandidateDomain> createCandidate(CreateCandidateEvent createCandidateEvent) {
    CandidateDetails candidateDetails = (CandidateDetails) createCandidateEvent.getDetails();
    Long userId = candidateDetails.getUserId();
    String userEmail = candidateDetails.getEmail();
    Long positionId = candidateDetails.getPositionId();
    if ((userId == null && Strings.isEmpty(userEmail) || positionId == null))
      return RequestHandledEvent.badRequest();

    // Check user existence
    User user = null;
    if (userId != null)
      user = userRepository.findById(userId, 0).orElse(null);
    else if (userEmail != null)
      user = userRepository.findByEmail(userEmail, 0);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    // Check position existence
    Position position = null;
    if (positionId != null)
      position = positionRepository.findById(positionId).orElse(null);
    if (position == null)
      return RequestHandledEvent.targetNotFound();
    // Check election existence
    Election election = position.getElection$();  //TODO may not work
    if (election == null)
      return RequestHandledEvent.targetNotFound();
    // Check if user is already a candidate to the election - Rule 1
    Integer i = candidateRepository.countUserToElectionChains(user.getNodeId(), election.getNodeId());
    if (i > 0)
      return RequestHandledEvent.conflicted();

    // Setup candidate entity for creation
    Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
    candidate.setUser(user.toNode());
    candidate.setPosition(position.toNode());
    // If ticketId is presented
    Long ticketId = candidateDetails.getTicketDetails().getNodeId();
    if (ticketId != null) {
      // Check ticket existence
      Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
      if (ticket == null)
        return RequestHandledEvent.targetNotFound();
      // Check if user is already on the ticket - Rule 2
      Integer j = candidateRepository.countUserToTicketChains(user.getNodeId(), ticket.getNodeId());
      if (j > 0)
        return RequestHandledEvent.conflicted();
      // Check if the election of Ticket is the same election or the position - Rule 4
      if (ticket.getElection().getNodeId() != election.getNodeId())
        return RequestHandledEvent.conflicted();
      candidate.setTicket(ticket.toNode());
    }
    // Save candidate entity
    Candidate result = candidateRepository.save(candidate);
    if (null == result)
      return RequestHandledEvent.failed();

    return new RequestHandledEvent<>(CandidateDomain.fromCandidateDetails(result.toCandidateDetails()), HttpStatus.CREATED);

  }

  @Override
  public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent) {
    Candidate candidate = candidateRepository.findById(requestReadCandidateEvent.getNodeId()).get();
    ReadEvent readCandidateEvent;
    if (candidate != null) {
      readCandidateEvent = new CandidateReadEvent(candidate.getNodeId(), candidate.toCandidateDetails());
    } else {
      readCandidateEvent = CandidateReadEvent.notFound(requestReadCandidateEvent.getNodeId());
    }
    return readCandidateEvent;
  }

  @Override
  public AllReadEvent readCandidates(ReadAllEvent readCandidatesEvent, Direction sortDirection, int pageNumber, int pageLength) {
    Long electionId = readCandidatesEvent.getParentId();
    Page<Candidate> candidates;
    ArrayList<CandidateDetails> dets = new ArrayList<>();
    AllReadEvent nare;

    if (LOG.isDebugEnabled()) LOG.debug("ElectionId " + electionId);
    Pageable pageable = PageRequest.of(pageNumber, pageLength, sortDirection, "e.name");
    candidates = candidateRepository.findByElectionId(electionId, pageable);
    if (candidates != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + candidates.getTotalElements() + " total pages =" + candidates.getTotalPages());
      Iterator<Candidate> iter = candidates.iterator();
      iter.forEachRemaining(candidate -> {
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + candidate.getNodeId());
        CandidateDetails det = candidate.toCandidateDetails();
        dets.add(det);
      });
      if (0 == dets.size()) {
        // Need to check if we actually found instId.
        Election elec = electionRepository.findById(electionId).get();
        if ((null == elec) ||
          ((null == elec.getTitle()) || ((null == elec.getStart()) && (null == elec.getEnd()) && (null == elec.getIntroduction())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ElectionId)");
          nare = AllReadEvent.notFound(electionId);
        } else {
          nare = new AllReadEvent(electionId, dets, candidates.getTotalElements(), candidates.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(electionId, dets, candidates.getTotalElements(), candidates.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(null);
    }
    return nare;
  }

  @Override
  public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent) {
    UpdatedEvent response;
    if (updateCandidateEvent != null) {
      CandidateDetails candidateDetails = (CandidateDetails) updateCandidateEvent.getDetails();
      Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
      Long candidateId = candidateDetails.getNodeId();
      if (LOG.isDebugEnabled()) LOG.debug("candidateId is " + candidateId);
      Candidate candidateOld = candidateRepository.findById(candidateId).get();
      if (candidateOld == null) {
        if (LOG.isDebugEnabled())
          LOG.debug("candidate entity not found " + candidateId);
        response = UpdatedEvent.notFound(candidateId);
      } else {
        if (null == candidate.getInformation())
          candidate.setInformation(candidateOld.getInformation());
        if (null == candidate.getPolicyStatement())
          candidate.setPolicyStatement(candidateOld.getPolicyStatement());
//        if (null == candidate.getPosition())
//          candidate.setPosition(candidateOld.getPosition());
//        if (null == candidate.getVoters$())
//          candidate.setVoters(candidateOld.getVoters$());
//        if (null == candidate.getTicket())
//          candidate.setTicket(candidateOld.getTicket());
//        if (null == candidate.getPhotos())
//          candidate.setPhotos(Node.castList(candidateOld.getPhotos()));


        Candidate result = candidateRepository.save(candidate, 0);
        if (result != null) {
          if (LOG.isDebugEnabled())
            LOG.debug("updated successfully" + result.getNodeId());
          response = new UpdatedEvent(result.getNodeId(), result.toCandidateDetails());
        } else
          response = UpdatedEvent.failed(candidateId);
      }
    } else {
      response = UpdatedEvent.notFound(null);
    }
    return response;
  }

  @Override
  public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteCandidateEvent= " + deleteCandidateEvent);
    Long candidateId = deleteCandidateEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("deleteCandidate(" + candidateId + ")");
    Candidate candidate = candidateRepository.findById(candidateId).get();
    if (candidate == null) {
      return CandidateDeletedEvent.notFound(candidateId);
    } else {
      candidateRepository.delete(candidate);
      return new CandidateDeletedEvent(candidateId);
    }
  }

  @Override
  public RequestHandledEvent<Boolean> addTicket(AddTicketEvent addTicketEvent) {
    UpdatedEvent ticketAddedEvent;
    Long candidateId = addTicketEvent.getCandidateId();
    Long ticketId = addTicketEvent.getTicketId();
    if (candidateId == null || ticketId == null)
      return RequestHandledEvent.badRequest();

    Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
    Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
    if (candidate == null || ticket == null)
      return RequestHandledEvent.targetNotFound();

    // Check if the user is already a candidate on ticket - Rule 2
    Long userId = candidate.getUser().getNodeId();
    Integer i = candidateRepository.countUserToTicketChains(userId, ticketId);
    if (i>0)
      return RequestHandledEvent.conflicted();

    // Check if the election of Ticket is the same election or the position - Rule 4
    Election electionOfCandidate = electionRepository.findByPositionId(candidate.getPosition().getNodeId());
    if (electionOfCandidate.getNodeId() != ticket.getElection().getNodeId())
      return RequestHandledEvent.conflicted();

    candidate.setTicket(ticket.toNode());
    Candidate savedCandidate = candidateRepository.save(candidate);
    if (savedCandidate == null)
      return RequestHandledEvent.failed();

    return new RequestHandledEvent<>(HttpStatus.OK);
  }

  @Override
  public UpdatedEvent removeTicket(DeleteEvent removeTicketEvent) {
    UpdatedEvent ticketRemovedEvent;
    Candidate candidate = candidateRepository.findById(removeTicketEvent.getNodeId()).get();
    if (candidate == null)
      ticketRemovedEvent = UpdatedEvent.notFound(removeTicketEvent.getNodeId());
    else {
      candidate.prune();
      candidate.setTicket(null);
      Candidate savedCandidate = candidateRepository.save(candidate);
      if (savedCandidate != null)
        ticketRemovedEvent = new UpdatedEvent(removeTicketEvent.getNodeId());
      else
        ticketRemovedEvent = UpdatedEvent.failed(removeTicketEvent.getNodeId());
    }
    return ticketRemovedEvent;
  }
}
