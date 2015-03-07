package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.*;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author Yikai Gong
 */

public class CandidateEventHandler implements CandidateService {
    private static Logger LOG = LoggerFactory.getLogger(CandidateService.class);

    private CandidateRepository candidateRepository;
    private UserRepository userRepository;
    private PositionRepository positionRepository;
    private ElectionRepository electionRepository;
    private TicketRepository ticketRepository;

    public CandidateEventHandler(CandidateRepository candidateRepository,
                                 UserRepository userRepository,
                                 PositionRepository positionRepository,
                                 ElectionRepository electionRepository,
                                 TicketRepository ticketRepository)
    {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.electionRepository = electionRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public CreatedEvent createCandidate(CreateCandidateEvent createCandidateEvent)
    {
        CandidateDetails candidateDetails = (CandidateDetails) createCandidateEvent.getDetails();
        CreatedEvent candidateCreatedEvent;
        
        Long userId=candidateDetails.getUserId();
    	if (LOG.isDebugEnabled()) LOG.debug("Finding user with nodeId = "+userId);
    	
        User user=null;
        if (userId!=null) user = userRepository.findOne(userId);
        
        if (user!=null)
        {
	        Long positionId=candidateDetails.getPositionId();
	    	if (LOG.isDebugEnabled()) LOG.debug("Finding position with nodeId = "+positionId);
	        Position position=null;
	        if (positionId!=null) position=positionRepository.findOne(positionId);
	    	if (position!=null)
	    	{
		        Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
		        candidate.setUser(user);
		        candidate.setPosition(position);
		        Candidate result = candidateRepository.save(candidate);
		        if ((null==result)||(null==result.getNodeId()))
		        	candidateCreatedEvent = CreatedEvent.failed(candidateDetails);
		        else
		        	candidateCreatedEvent = new CandidateCreatedEvent(result.toCandidateDetails());
	    	}
	    	else
	    	{
	    		candidateCreatedEvent=CandidateCreatedEvent.positionNotFound(candidateDetails.getPositionId());
	    	}
        }
        else
        {
    		candidateCreatedEvent=CandidateCreatedEvent.userNotFound(candidateDetails.getUserId());
        }
        return candidateCreatedEvent;
    }
    
    @Override
    public ReadEvent requestReadCandidate(RequestReadCandidateEvent requestReadCandidateEvent) {
        Candidate candidate = candidateRepository.findOne(requestReadCandidateEvent.getNodeId());
        ReadEvent readCandidateEvent;
        if(candidate != null){
            readCandidateEvent = new CandidateReadEvent(candidate.getNodeId(), candidate.toCandidateDetails());
        }
        else{
            readCandidateEvent = CandidateReadEvent.notFound(requestReadCandidateEvent.getNodeId());
        }
        return readCandidateEvent;
    }

	@Override
	public CandidatesReadEvent readCandidates(ReadAllEvent readCandidatesEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Long electionId=readCandidatesEvent.getParentId();
		Page <Candidate>candidates=null;
		ArrayList<CandidateDetails> dets=new ArrayList<CandidateDetails>();
		CandidatesReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("ElectionId "+electionId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"e.name");
		candidates=candidateRepository.findByElectionId(electionId, pageable);
		if (candidates!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+candidates.getTotalElements()+" total pages ="+candidates.getTotalPages());
			Iterator<Candidate> iter=candidates.iterator();
			while (iter.hasNext())
			{
				Candidate na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getNodeId());
				CandidateDetails det=na.toCandidateDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found instId.
				Election elec=electionRepository.findOne(electionId);
				if ( (null==elec) ||
					 ((null==elec.getTitle()) || ((null==elec.getStart()) && (null==elec.getEnd()) && (null==elec.getIntroduction()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ElectionId)");
					nare=CandidatesReadEvent.electionNotFound();
				}
				else
				{	
					nare=new CandidatesReadEvent(electionId,dets,candidates.getTotalElements(),candidates.getTotalPages());
				}
			}
			else
			{	
				nare=new CandidatesReadEvent(electionId,dets,candidates.getTotalElements(),candidates.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=CandidatesReadEvent.electionNotFound();
		}
		return nare;
	}

	@Override
    public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent) {
        CandidateDetails candidateDetails = (CandidateDetails) updateCandidateEvent.getDetails();
        Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
        Long candidateId = candidateDetails.getNodeId();
        if(LOG.isDebugEnabled()) LOG.debug("candidateId is " + candidateId);
        Candidate candidateOld = candidateRepository.findOne(candidateId);
        if(candidateOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("candidate entity not found " + candidateId);
            return CandidateUpdatedEvent.notFound(candidateId);
        }
        else{
            Candidate result = candidateRepository.save(candidate);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new CandidateUpdatedEvent(result.getNodeId(), result.toCandidateDetails());
        }
    }

    @Override
    public DeletedEvent deleteCandidate(DeleteCandidateEvent deleteCandidateEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteCandidateEvent= "+deleteCandidateEvent);
        Long candidateId = deleteCandidateEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteCandidate("+candidateId+")");
        Candidate candidate = candidateRepository.findOne(candidateId);
        if(candidate == null){
            return CandidateDeletedEvent.notFound(candidateId);
        }
        else{
            candidateRepository.delete(candidate);
            CandidateDeletedEvent candidateDeletedEvent = new CandidateDeletedEvent(candidateId);
            return candidateDeletedEvent;
        }
    }

    @Override
    public TicketAddedEvent addTicket(AddTicketEvent addTicketEvent) {
        TicketAddedEvent ticketAddedEvent = null;
        Candidate candidate = candidateRepository.findOne(addTicketEvent.getCandidateId());
        Ticket ticket = ticketRepository.findOne(addTicketEvent.getTicketId());
        if(candidate == null)
            ticketAddedEvent = TicketAddedEvent.candidateNotFound(addTicketEvent.getCandidateId(), addTicketEvent.getTicketId());
        else if(ticket == null)
            ticketAddedEvent = TicketAddedEvent.ticketNotFound(addTicketEvent.getCandidateId(), addTicketEvent.getTicketId());
        else {
            ticketAddedEvent = new TicketAddedEvent(addTicketEvent.getCandidateId(), addTicketEvent.getTicketId());
            IsOnTicket isOnTicket = candidateRepository.createIsOnTicketRelationship(addTicketEvent.getCandidateId(), addTicketEvent.getTicketId());
            if (isOnTicket == null)
                ticketAddedEvent.setResult(false);
        }
        return ticketAddedEvent;
    }

    @Override
    public TicketRemovedEvent removeTicket(RemoveTicketEvent removeTicketEvent) {
        TicketRemovedEvent ticketRemovedEvent = null;
        Candidate candidate = candidateRepository.findOne(removeTicketEvent.getCandidateId());
        Ticket ticket = ticketRepository.findOne(removeTicketEvent.getTicketId());
        if(candidate == null)
            ticketRemovedEvent = TicketRemovedEvent.candidateNotFound(removeTicketEvent.getCandidateId(), removeTicketEvent.getCandidateId());
        else if(ticket == null)
            ticketRemovedEvent = TicketRemovedEvent.ticketNotFound(removeTicketEvent.getCandidateId(), removeTicketEvent.getTicketId());
        else{
            candidateRepository.deleteIsOnTicketRelationship(removeTicketEvent.getCandidateId(), removeTicketEvent.getTicketId());
            ticketRemovedEvent = new TicketRemovedEvent(removeTicketEvent.getCandidateId(), removeTicketEvent.getTicketId());
        }
        return ticketRemovedEvent;
    }
}
