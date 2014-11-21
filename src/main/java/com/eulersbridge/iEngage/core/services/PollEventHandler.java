package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.repository.PollRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Yikai Gong
 */

public class PollEventHandler implements PollService{
    private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandler.class);
//    @Autowired
    private PollRepository pollRepository;

    public PollEventHandler(PollRepository pollRepository){
        this.pollRepository = pollRepository;
    }

    @Override
    public ReadEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent) {
        Poll poll = pollRepository.findOne(requestReadPollEvent.getPollId());
        ReadEvent readPollEvent;
        if(poll != null){
            readPollEvent = new ReadPollEvent(requestReadPollEvent.getPollId(), poll.toPollDetails());
        }
        else{
            readPollEvent = ReadPollEvent.notFound(requestReadPollEvent.getPollId());
        }
        return readPollEvent;
    }

    @Override
    public PollCreatedEvent createPoll(CreatePollEvent createPollEvent) {
        PollDetails pollDetails = createPollEvent.getPollDetails();
        Poll poll = Poll.fromPollDetails(pollDetails);
        Poll result = pollRepository.save(poll);
        PollCreatedEvent pollCreatedEvent = new PollCreatedEvent(result.getPollId(), result.toPollDetails());
        return pollCreatedEvent;
    }

    @Override
    public DeletedEvent deletePoll(DeletePollEvent deletePollEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePollEvent= "+deletePollEvent);
        Long pollId = deletePollEvent.getPollId();
        if (LOG.isDebugEnabled()) LOG.debug("deletePoll("+pollId+")");
        Poll poll = pollRepository.findOne(pollId);
        if(poll == null){
            return PollDeletedEvent.notFound(pollId);
        }
        else{
            pollRepository.delete(pollId);
            PollDeletedEvent pollDeletedEvent = new PollDeletedEvent(pollId);
            return pollDeletedEvent;
        }
    }

    @Override
    public PollUpdatedEvent updatePoll(UpdatePollEvent updatePollEvent) {
        PollDetails pollDetails = updatePollEvent.getPollDetails();
        Poll poll = Poll.fromPollDetails(pollDetails);
        Long pollId = pollDetails.getPollId();
        if(LOG.isDebugEnabled()) LOG.debug("poll Id is " + pollId);
        Poll pollOld = pollRepository.findOne(pollId);
        if(pollOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("poll entity not found " + pollId);
            return PollUpdatedEvent.notFound(pollId);
        }
        else{
            Poll result = pollRepository.save(poll);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getPollId());
            return new PollUpdatedEvent(result.getPollId(), result.toPollDetails());
        }
    }

}
