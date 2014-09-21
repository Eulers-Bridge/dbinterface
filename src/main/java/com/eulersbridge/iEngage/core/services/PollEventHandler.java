package com.eulersbridge.iEngage.core.services;

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
    public ReadPollEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent) {
        Poll poll = pollRepository.findOne(requestReadPollEvent.getPollId());
        ReadPollEvent readPollEvent;
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
    public PollDeletedEvent deletePoll(DeletePollEvent deletePollEvent) {
        return null;
    }

    @Override
    public PollUpdatedEvent updatePoll(UpdatePollEvent updatePollEvent) {
        return null;
    }
}
