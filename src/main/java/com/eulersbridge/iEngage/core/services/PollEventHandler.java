package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.polls.*;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.PollAnswerRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.neo4j.conversion.Result;

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
	    		poll.setCreator(new User(creator.getNodeId()));
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
		    		poll.setCreator(new User(creator.getNodeId()));
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
	    		pollAnswer.setUser(owner);
	    		pollAnswer.setPoll(poll);
	    		Integer answerIndex=pollAnswer.getAnswer();
	    		String answers=poll.getAnswers();
	    		String [] answerArray=answers.split(",");
	    		int numAnswers=answerArray.length;
	    		if  ((answerIndex==null)||(answerIndex<0)||(answerIndex>numAnswers))
	    			answerCreatedEvent=PollAnswerCreatedEvent.badAnswer(answerDetails.getAnswerIndex());
	    		else
	    		{
	    			if (LOG.isDebugEnabled()) LOG.debug("pollAnswer - "+pollAnswer);
	    			if (LOG.isDebugEnabled()) LOG.debug("userId - "+answerDetails.getAnswererId()+" pollId - "+ answerDetails.getPollId()+" answer Index - "+ answerDetails.getAnswerIndex());
//	    			PollAnswer result = answerRepository.save(pollAnswer);
		    		PollAnswer result = pollRepository.addPollAnswer(answerDetails.getAnswererId(), answerDetails.getPollId(), answerDetails.getAnswerIndex());
		        	if (LOG.isDebugEnabled()) LOG.debug("result - "+result);
		    		answerCreatedEvent = new PollAnswerCreatedEvent( result.toPollAnswerDetails());
	    		}
	    	}
    	}
		return answerCreatedEvent;
	}

	@Override
	public ReadEvent readPollResult(
			ReadPollResultEvent readPollResultEvent)
	{
		Long pollId=readPollResultEvent.getNodeId();
		if (LOG.isDebugEnabled()) LOG.debug("Finding results for poll - "+pollId);
		Poll poll = pollRepository.findOne(pollId);
		ReadEvent pollResultReadEvent=null;
		if (poll != null)
		{
			if (LOG.isDebugEnabled()) LOG.debug("poll - "+poll);
			Result<PollResultTemplate> results=pollRepository.getPollResults(pollId);
			if (results!=null)
			{
				if (LOG.isDebugEnabled()) LOG.debug("Got results");
				String answers[]=poll.getAnswers().split(",");
				int numAnswers=answers.length;
				ArrayList <PollResult> resultDetails=new ArrayList<PollResult>();

                resultDetails=PollResultDetails.toPollResultList(results.iterator(),numAnswers);
				PollResultDetails dets=new PollResultDetails(pollId, resultDetails);
				pollResultReadEvent = new PollResultReadEvent(pollId,dets);
			}
			else
			{
				if (LOG.isDebugEnabled()) LOG.debug("No results");
				pollResultReadEvent = PollResultReadEvent.notFound(readPollResultEvent.getNodeId());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("No poll");
			pollResultReadEvent = PollResultReadEvent.notFound(readPollResultEvent.getNodeId());
		}
		return pollResultReadEvent;
	}

	@Override
	public AllReadEvent findPolls(ReadAllEvent readPollsEvent,
			Direction dir, int pageNumber, int pageLength)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered findPolls readPollsEvent = "+readPollsEvent);
        Long ownerId = readPollsEvent.getParentId();
		Page <Poll>polls=null;
		ArrayList<PollDetails> dets=new ArrayList<PollDetails>();
        
		AllReadEvent result=null;
		
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
				Owner inst=ownerRepository.findOne(ownerId);
				if ( (null==inst) || (null==inst.getNodeId()) )
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ownerId)");
					result=AllReadEvent.notFound(null);
				}
				else
				{	
					result=new AllReadEvent(ownerId,dets,polls.getTotalElements(),polls.getTotalPages());
				}
			}
			else
			{	
				result=new AllReadEvent(ownerId,dets,polls.getTotalElements(),polls.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
			result=AllReadEvent.notFound(null);
		}
		return result;
	}
}
