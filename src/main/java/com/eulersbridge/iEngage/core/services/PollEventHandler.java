package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PollEventHandler implements PollService
{
	private static Logger LOG = LoggerFactory
			.getLogger(ElectionEventHandler.class);
	// @Autowired
	private PollRepository pollRepository;
	private OwnerRepository ownerRepository;

	public PollEventHandler(PollRepository pollRepository, OwnerRepository ownerRepository)
	{
		this.pollRepository = pollRepository;
		this.ownerRepository = ownerRepository;
	}

	@Override
	public ReadEvent requestReadPoll(RequestReadPollEvent requestReadPollEvent)
	{
		Poll poll = pollRepository.findOne(requestReadPollEvent.getNodeId());
		ReadEvent readPollEvent;
		if (poll != null)
		{
			readPollEvent = new ReadPollEvent(requestReadPollEvent.getNodeId(),
					poll.toPollDetails());
		}
		else
		{
			readPollEvent = ReadPollEvent.notFound(requestReadPollEvent
					.getNodeId());
		}
		return readPollEvent;
	}

	@Override
	public PollCreatedEvent createPoll(CreatePollEvent createPollEvent)
	{
		PollDetails pollDetails = (PollDetails) createPollEvent.getDetails();
		Poll poll = Poll.fromPollDetails(pollDetails);
		
		if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+pollDetails.getOwnerId());
    	Owner owner=ownerRepository.findOne(pollDetails.getOwnerId());
    	PollCreatedEvent pollCreatedEvent;
    	if (null==owner)
    		pollCreatedEvent=PollCreatedEvent.ownerNotFound(pollDetails.getOwnerId());
    	else
    	{
    		
			if (LOG.isDebugEnabled()) LOG.debug("Finding creator with creatorId = "+pollDetails.getCreatorId());
	    	Owner creator=ownerRepository.findOne(pollDetails.getCreatorId());
	
	    	if (null==creator)
	    		pollCreatedEvent=PollCreatedEvent.creatorNotFound(pollDetails.getCreatorId());
	    	else
	    	{
	    		poll.setOwner(owner);
	    		poll.setCreator(creator);
	    		Poll result = pollRepository.save(poll);
	        	pollCreatedEvent = new PollCreatedEvent( result.toPollDetails());
	    	}

    	}
		return pollCreatedEvent;
	}

	@Override
	public DeletedEvent deletePoll(DeletePollEvent deletePollEvent)
	{
		if (LOG.isDebugEnabled())
			LOG.debug("Entered deletePollEvent= " + deletePollEvent);
		Long pollId = deletePollEvent.getNodeId();
		if (LOG.isDebugEnabled()) LOG.debug("deletePoll(" + pollId + ")");
		Poll poll = pollRepository.findOne(pollId);
		if (poll == null)
		{
			return PollDeletedEvent.notFound(pollId);
		}
		else
		{
			pollRepository.delete(pollId);
			PollDeletedEvent pollDeletedEvent = new PollDeletedEvent(pollId);
			return pollDeletedEvent;
		}
	}

	@Override
	public UpdatedEvent updatePoll(UpdatePollEvent updatePollEvent)
	{
		PollDetails pollDetails = (PollDetails) updatePollEvent.getDetails();
		Poll poll = Poll.fromPollDetails(pollDetails);
		Long pollId = pollDetails.getPollId();
	    UpdatedEvent resultEvt;
		if (LOG.isDebugEnabled()) LOG.debug("poll Id is " + pollId);
		Poll pollOld = pollRepository.findOne(pollId);
		if (pollOld == null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("poll entity not found " + pollId);
			resultEvt=PollUpdatedEvent.notFound(pollId);
		}
		else
		{
			Poll result = pollRepository.save(poll);
			if (LOG.isDebugEnabled())
				LOG.debug("updated successfully" + result.getNodeId());
			resultEvt =  new PollUpdatedEvent(result.getNodeId(),
					result.toPollDetails());
		}
	    return resultEvt;		
	}
}
