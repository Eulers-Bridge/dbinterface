package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollAnswer;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.PollAnswerRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author Yikai Gong
 */

public class PollEventHandler implements PollService
{
	private static Logger LOG = LoggerFactory
			.getLogger(ElectionEventHandler.class);
	// @Autowired
	private PollRepository pollRepository;
	private PollAnswerRepository answerRepository;
	private OwnerRepository ownerRepository;

	public PollEventHandler(PollRepository pollRepository, PollAnswerRepository answerRepository, OwnerRepository ownerRepository)
	{
		this.pollRepository = pollRepository;
		this.answerRepository=answerRepository;
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
			if (LOG.isDebugEnabled()) LOG.debug("Finding owner with ownerId = "+pollDetails.getOwnerId());
			Owner owner=null;
			if (null!=pollDetails.getOwnerId())
				owner=ownerRepository.findOne(pollDetails.getOwnerId());
	    	if (null==owner)
	    		resultEvt=PollUpdatedEvent.ownerNotFound(pollDetails.getOwnerId());
	    	else
	    	{
	    		
				if (LOG.isDebugEnabled()) LOG.debug("Finding creator with creatorId = "+pollDetails.getCreatorId());
				Owner creator=null;
				if (null!=pollDetails.getCreatorId())
					creator=ownerRepository.findOne(pollDetails.getCreatorId());
		
		    	if (null==creator)
		    		resultEvt=PollUpdatedEvent.creatorNotFound(pollDetails.getCreatorId());
		    	else
		    	{
		    		poll.setOwner(owner);
		    		poll.setCreator(creator);
		    		Poll result = pollRepository.save(poll);
		    		resultEvt = new PollUpdatedEvent( result.getNodeId(), result.toPollDetails());
		    	}
	    	}
		}
	    return resultEvt;		
	}
	
	@Override
	public PollAnswerCreatedEvent answerPoll(
			CreatePollAnswerEvent pollAnswerEvent)
	{
		PollAnswerDetails answerDetails = (PollAnswerDetails) pollAnswerEvent.getDetails();
		PollAnswer pollAnswer = PollAnswer.fromPollAnswerDetails(answerDetails);
		
		if (LOG.isDebugEnabled()) LOG.debug("Finding owner with answererId = "+answerDetails.getAnswererId());
    	Owner owner=ownerRepository.findOne(answerDetails.getAnswererId());
    	PollAnswerCreatedEvent answerCreatedEvent;
    	if (null==owner)
    		answerCreatedEvent=PollAnswerCreatedEvent.answererNotFound(answerDetails.getAnswererId());
    	else
    	{
			if (LOG.isDebugEnabled()) LOG.debug("Finding poll with pollId = "+answerDetails.getPollId());
	    	Poll poll=pollRepository.findOne(answerDetails.getPollId());
	
	    	if (null==poll)
	    		answerCreatedEvent=PollAnswerCreatedEvent.pollNotFound(answerDetails.getPollId());
	    	else
	    	{
	    		pollAnswer.setAnswerer(owner);
	    		pollAnswer.setPoll(poll);
	    		PollAnswer result = answerRepository.save(pollAnswer);
	        	answerCreatedEvent = new PollAnswerCreatedEvent( result.toPollAnswerDetails());
	    	}
    	}
		return answerCreatedEvent;
	}

	@Override
	public PollsReadEvent findPolls(ReadPollsEvent readPollsEvent,
			Direction dir, int pageNumber, int pageLength)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered findPolls readPollsEvent = "+readPollsEvent);
        Long ownerId = readPollsEvent.getOwnerId();
		Page <Poll>polls=null;
		ArrayList<PollDetails> dets=new ArrayList<PollDetails>();
        
		PollsReadEvent result=null;
		
		if (LOG.isDebugEnabled()) LOG.debug("OwnerId "+ownerId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,dir,"p.date");
		polls=pollRepository.findByOwnerId(ownerId, pageable);

		if (polls!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+polls.getTotalElements()+" total pages ="+polls.getTotalPages());
			Iterator<Poll> iter=polls.iterator();
			while (iter.hasNext())
			{
				Poll na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getQuestion());
				PollDetails det=na.toPollDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found ownerId.
				Poll inst=pollRepository.findOne(ownerId);
				if ( (null==inst) || (null==inst.getNodeId()) )
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ownerId)");
					result=PollsReadEvent.institutionNotFound();
				}
				else
				{	
					result=new PollsReadEvent(ownerId,dets,polls.getTotalElements(),polls.getTotalPages());
				}
			}
			else
			{	
				result=new PollsReadEvent(ownerId,dets,polls.getTotalElements(),polls.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
			result=PollsReadEvent.institutionNotFound();
		}
		return result;
	}
}
