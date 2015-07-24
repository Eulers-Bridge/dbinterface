package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
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
        String userEmail=candidateDetails.getEmail();
    	
        User user=null;
        if (userId!=null)
        {
        	if (LOG.isDebugEnabled()) LOG.debug("Finding user with nodeId = "+userId);
        	user = userRepository.findOne(userId);
        }
        else if (userEmail!=null)
        {
        	if (LOG.isDebugEnabled()) LOG.debug("Finding user with email = "+userEmail);
        	user = userRepository.findByEmail(userEmail);
        }
        
        
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
	public AllReadEvent readCandidates(ReadAllEvent readCandidatesEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Long electionId=readCandidatesEvent.getParentId();
		Page <Candidate>candidates=null;
		ArrayList<CandidateDetails> dets=new ArrayList<CandidateDetails>();
		AllReadEvent nare=null;

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
					nare=AllReadEvent.notFound(electionId);
				}
				else
				{	
					nare=new AllReadEvent(electionId,dets,candidates.getTotalElements(),candidates.getTotalPages());
				}
			}
			else
			{	
				nare=new AllReadEvent(electionId,dets,candidates.getTotalElements(),candidates.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=AllReadEvent.notFound(null);
		}
		return nare;
	}

	@Override
    public UpdatedEvent updateCandidate(UpdateCandidateEvent updateCandidateEvent)
	{
		UpdatedEvent response;
		if (updateCandidateEvent!=null)
		{
	        CandidateDetails candidateDetails = (CandidateDetails) updateCandidateEvent.getDetails();
	        Candidate candidate = Candidate.fromCandidateDetails(candidateDetails);
	        Long candidateId = candidateDetails.getNodeId();
	        if(LOG.isDebugEnabled()) LOG.debug("candidateId is " + candidateId);
	        Candidate candidateOld = candidateRepository.findOne(candidateId);
	        if(candidateOld == null)
	        {
	            if(LOG.isDebugEnabled()) LOG.debug("candidate entity not found " + candidateId);
	            response = UpdatedEvent.notFound(candidateId);
	        }
	        else
	        {
				if (null==candidate.getInformation())
					candidate.setInformation(candidateOld.getInformation());
				if (null==candidate.getPolicyStatement())
					candidate.setPolicyStatement(candidateOld.getPolicyStatement());
				if (null==candidate.getPosition())
					candidate.setPosition(candidateOld.getPosition());
				if (null==candidate.getUser())
					candidate.setUser(candidateOld.getUser());
				if (null==candidate.getTicket())
					candidate.setTicket(candidateOld.getTicket());
				if (null==candidate.getPhotos())
					candidate.setPhotos(candidateOld.getPhotos());

	        	
	            Candidate result = candidateRepository.save(candidate);
	            if (result!=null)
	            {
	            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
	            response = new UpdatedEvent(result.getNodeId(), result.toCandidateDetails());
	            }
	            else
	            	response = UpdatedEvent.failed(candidateId);
	        }
		}
		else
		{
			response = UpdatedEvent.notFound(null);
		}
		return response;
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
    public UpdatedEvent addTicket(AddTicketEvent addTicketEvent) {
        UpdatedEvent ticketAddedEvent = null;
        Candidate candidate = candidateRepository.findOne(addTicketEvent.getCandidateId());
        Ticket ticket = ticketRepository.findOne(addTicketEvent.getTicketId());
        if(candidate == null)
            ticketAddedEvent = UpdatedEvent.notFound(addTicketEvent.getCandidateId());
        else if(ticket == null)
            ticketAddedEvent = UpdatedEvent.notFound(addTicketEvent.getTicketId());
        else 
        {
            candidate.setTicket(ticket);
            Candidate savedCandidate=candidateRepository.save(candidate);
            if (savedCandidate == null)
            	ticketAddedEvent = UpdatedEvent.failed(addTicketEvent.getCandidateId());
            else
                ticketAddedEvent = new UpdatedEvent(addTicketEvent.getCandidateId());
        }
        return ticketAddedEvent;
    }

    @Override
    public UpdatedEvent removeTicket(DeleteEvent removeTicketEvent) {
    	UpdatedEvent ticketRemovedEvent = null;
        Candidate candidate = candidateRepository.findOne(removeTicketEvent.getNodeId());
        if(candidate == null)
            ticketRemovedEvent = UpdatedEvent.notFound(removeTicketEvent.getNodeId());
        else
        {
        	candidate.setTicket(null);
            Candidate savedCandidate=candidateRepository.save(candidate);
        	if (savedCandidate!=null)
        		ticketRemovedEvent = new UpdatedEvent(removeTicketEvent.getNodeId());
        	else
                ticketRemovedEvent = UpdatedEvent.failed(removeTicketEvent.getNodeId());
        }
        return ticketRemovedEvent;
    }
}
