package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.core.services.interfacePack.PollService;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yikai Gong
 */

@Service
public class PollEventHandler implements PollService {
  private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandler.class);

  private PollRepository pollRepository;
  private PollOptionRepository pollOptionRepository;
  private InstitutionRepository institutionRepository;
  private final UserRepository userRepo;

  private Session session;

  //TODO there is no session bean currently. Why need it?
  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  public PollEventHandler(PollRepository pollRepository,
                          InstitutionRepository institutionRepository, Session session, UserRepository userRepo,
                          PollOptionRepository pollOptionRepository) {
    this.pollRepository = pollRepository;
    this.institutionRepository = institutionRepository;
    this.session = session;
    this.userRepo = userRepo;
    this.pollOptionRepository = pollOptionRepository;
  }

  @Override
  public ReadEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent) {
    Poll poll = pollRepository.findOneCustom(requestReadPollEvent.getNodeId());
    ReadEvent readPollEvent;
    if (poll != null) {
      readPollEvent = new ReadPollEvent(requestReadPollEvent.getNodeId(),
        poll.toPollDetails());
    } else {
      readPollEvent = ReadPollEvent.notFound(requestReadPollEvent
        .getNodeId());
    }
    return readPollEvent;
  }

  @Override
  public PollCreatedEvent createPoll(CreatePollEvent createPollEvent) {
    PollCreatedEvent pollCreatedEvent;
    PollDetails pollDetails = (PollDetails) createPollEvent.getDetails();
    Poll poll = Poll.fromPollDetails(pollDetails);

    Node creator = userRepo.findByEmail(pollDetails.getCreatorEmail());
    if (creator == null)
      return PollCreatedEvent.creatorNotFound(0l);

    Institution institution = institutionRepository.findInstitutionByUserEMail(pollDetails.getCreatorEmail());
    if (institution == null)
      return PollCreatedEvent.ownerNotFound(pollDetails.getOwnerId());

    poll.setInstitution(institution.toNode());
    poll.setCreator(creator.toNode());
    Poll result = pollRepository.save(poll);
    pollCreatedEvent = new PollCreatedEvent(result.toPollDetails());
    return pollCreatedEvent;
  }

  @Override
  public RequestHandledEvent votePollOption(String userEmail, Long pollId, Long optionId) {
    User user = userRepo.findByEmail(userEmail, 0);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    PollOption option = pollOptionRepository.findPollOptionAndPoll(optionId);
    if (option == null)
      return RequestHandledEvent.targetNotFound();
    if (!option.getPoll$().getNodeId().equals(pollId))
      return RequestHandledEvent.badRequest();
    Poll poll = option.getPoll$();
    Long pollEndTime = poll.getStart() + poll.getDuration();
    Long current = new Date().getTime();
    if(poll.getStart()>current)
      return RequestHandledEvent.canNotModiry();
    if (pollEndTime < current)
      return RequestHandledEvent.premissionExpired();
    PollOption votedPollOpt = pollOptionRepository.checkIfUserHasVoted(pollId, userEmail);
    if (votedPollOpt != null)
      return RequestHandledEvent.notAllowed();

    pollOptionRepository.votePollOption(userEmail, optionId);
    return new RequestHandledEvent();
  }

  @Override
  public DeletedEvent deletePoll(DeletePollEvent deletePollEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deletePollEvent= " + deletePollEvent);
    Long pollId = deletePollEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("deletePoll(" + pollId + ")");
    Poll poll = pollRepository.findOne(pollId);
    if (poll == null) {
      return PollDeletedEvent.notFound(pollId);
    } else {
      pollRepository.delete(pollId);
      PollDeletedEvent pollDeletedEvent = new PollDeletedEvent(pollId);
      return pollDeletedEvent;
    }
  }

  @Override
  public UpdatedEvent updatePoll(UpdatePollEvent updatePollEvent) {
    PollDetails pollDetails = (PollDetails) updatePollEvent.getDetails();
    Poll poll = Poll.fromPollDetails(pollDetails);
    Long pollId = pollDetails.getPollId();
    UpdatedEvent resultEvt;

    if (LOG.isDebugEnabled()) LOG.debug("poll Id is " + pollId);
    Poll pollOld = pollRepository.findOne(pollId);
    if (pollOld == null) {
      if (LOG.isDebugEnabled())
        LOG.debug("poll entity not found " + pollId);
      resultEvt = PollUpdatedEvent.notFound(pollId);
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Finding owner with ownerId = " + pollDetails.getOwnerId());
      Institution institution = null;
      if (null != pollDetails.getOwnerId())
        institution = institutionRepository.findOne(pollDetails.getOwnerId());
      if (null == institution)
        resultEvt = PollUpdatedEvent.ownerNotFound(pollDetails.getOwnerId());
      else {

        if (LOG.isDebugEnabled())
          LOG.debug("Finding creator with creatorId = " + pollDetails.getCreatorId());
        Node creator = null;
        if (null != pollDetails.getCreatorId())
          creator = userRepo.findOne(pollDetails.getCreatorId());

        if (null == creator)
          resultEvt = PollUpdatedEvent.creatorNotFound(pollDetails.getCreatorId());
        else {
          poll.setInstitution(institution.toNode());
          poll.setCreator((new User(creator.getNodeId())).toNode());
          Poll result = pollRepository.save(poll, 0);
          resultEvt = new PollUpdatedEvent(result.getNodeId(), result.toPollDetails());
        }
      }
    }
    return resultEvt;
  }

  @Override
  public ReadEvent readPollResult(
    ReadPollResultEvent readPollResultEvent) {
    Long pollId = readPollResultEvent.getNodeId();
    if (LOG.isDebugEnabled()) LOG.debug("Finding results for poll - " + pollId);
    Poll poll = pollRepository.findOne(pollId);
    ReadEvent pollResultReadEvent = null;
    if (poll != null) {
      if (LOG.isDebugEnabled()) LOG.debug("poll - " + poll);
      List<PollResultTemplate> results = pollRepository.getPollResults(pollId);
      if (results != null) {
        if (LOG.isDebugEnabled()) LOG.debug("Got results");
        String answers[] = "".split(",");
        int numAnswers = answers.length;
        ArrayList<PollResult> resultDetails = new ArrayList<PollResult>();

        resultDetails = PollResultDetails.toPollResultList(results.iterator(), numAnswers);
        PollResultDetails dets = new PollResultDetails(pollId, resultDetails);
        pollResultReadEvent = new PollResultReadEvent(pollId, dets);
      } else {
        if (LOG.isDebugEnabled()) LOG.debug("No results");
        pollResultReadEvent = PollResultReadEvent.notFound(readPollResultEvent.getNodeId());
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("No poll");
      pollResultReadEvent = PollResultReadEvent.notFound(readPollResultEvent.getNodeId());
    }
    return pollResultReadEvent;
  }

  @Override
  public AllReadEvent findPolls(ReadAllEvent readPollsEvent,
                                Direction dir, int pageNumber, int pageLength) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered findPolls readPollsEvent = " + readPollsEvent);
    Long ownerId = readPollsEvent.getParentId();
    Page<Poll> polls = null;
    ArrayList<PollDetails> dets = new ArrayList<PollDetails>();

    AllReadEvent result = null;

    Pageable pageable = new PageRequest(pageNumber, pageLength, dir, "p.date");
    polls = pollRepository.findByOwnerId(ownerId, pageable);

    if (polls != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + polls.getTotalElements() + " total pages =" + polls.getTotalPages());
      Iterator<Poll> iter = polls.iterator();
      while (iter.hasNext()) {
        Poll na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getQuestion());
        PollDetails det = na.toPollDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found ownerId.
        Node inst = institutionRepository.findOne(ownerId);
        if ((null == inst) || (null == inst.getNodeId())) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(ownerId)");
          result = AllReadEvent.notFound(null);
        } else {
          result = new AllReadEvent(ownerId, dets, polls.getTotalElements(), polls.getTotalPages());
        }
      } else {
        result = new AllReadEvent(ownerId, dets, polls.getTotalElements(), polls.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
      result = AllReadEvent.notFound(null);
    }
    return result;
  }
}
