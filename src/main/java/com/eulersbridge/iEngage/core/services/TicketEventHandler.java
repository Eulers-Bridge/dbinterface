package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.core.events.ticket.CreateTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.DeleteTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.ReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.RequestReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.SupportTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketCreatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDeletedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.ticket.TicketSupportedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketUpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent;
import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Support;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.resultMap.SupportAndNum;
import com.eulersbridge.iEngage.database.repository.CandidateRepository;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.TicketRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author Yikai Gong
 */

public class TicketEventHandler implements TicketService{
    private static Logger LOG = LoggerFactory.getLogger(TicketService.class);

    private TicketRepository ticketRepository;
    private ElectionRepository electionRepository;
    private UserRepository userRepository;
    private CandidateRepository candidateRepository;

    public TicketEventHandler(TicketRepository ticketRepository, ElectionRepository electionRepository, UserRepository userRepository, CandidateRepository candidateRepository)
    {
        this.ticketRepository = ticketRepository;
        this.electionRepository = electionRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public CreatedEvent createTicket(CreateTicketEvent createTicketEvent) 
    {
        TicketDetails ticketDetails = (TicketDetails) createTicketEvent.getDetails();
    	Long electionId=ticketDetails.getElectionId();
        
    	if (LOG.isDebugEnabled()) LOG.debug("Finding election with nodeId = "+electionId);
    	TicketCreatedEvent ticketCreatedEvent;

    	if (electionId!=null)
    	{
	    	Election elect=electionRepository.findOne(electionId);
	
	    	if (elect!=null)
	    	{
	            Ticket ticket = Ticket.fromTicketDetails(ticketDetails);
	            ticket.setElection(elect);
	            Ticket result = ticketRepository.save(ticket);
	            ticketCreatedEvent = new TicketCreatedEvent(result.toTicketDetails());
	    	}
	    	else
	    	{
	    		ticketCreatedEvent=TicketCreatedEvent.electionNotFound(electionId);
	    	}
    	}
    	else
    	{
    		ticketCreatedEvent=TicketCreatedEvent.electionNotFound(electionId);
    	}
        return ticketCreatedEvent;
    }

    @Override
    public ReadEvent requestReadTicket(RequestReadTicketEvent requestReadTicketEvent) {
        Ticket ticket = ticketRepository.findOne(requestReadTicketEvent.getNodeId());
        ReadEvent readTicketEvent;
        if(ticket != null){
            readTicketEvent = new ReadTicketEvent(ticket.getNodeId(), ticket.toTicketDetails());
        }
        else{
            readTicketEvent = ReadTicketEvent.notFound(requestReadTicketEvent.getNodeId());
        }
        return readTicketEvent;
    }

    @Override
    public UpdatedEvent updateTicket(UpdateTicketEvent updateTicketEvent) {
        TicketDetails ticketDetails = (TicketDetails) updateTicketEvent.getDetails();
        Ticket ticket = Ticket.fromTicketDetails(ticketDetails);
        Long ticketId = ticketDetails.getNodeId();
        if(LOG.isDebugEnabled()) LOG.debug("ticketId is " + ticketId);
        Ticket ticketOld = ticketRepository.findOne(ticketId);
        if(ticketOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("ticket entity not found " + ticketId);
            return TicketUpdatedEvent.notFound(ticketId);
        }
        else{
            Ticket result = ticketRepository.save(ticket);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new TicketUpdatedEvent(result.getNodeId(), result.toTicketDetails());
        }
    }

    @Override
    public DeletedEvent deleteTicket(DeleteTicketEvent deleteTicketEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteTicketEvent= "+deleteTicketEvent);
        Long ticketId = deleteTicketEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteTicket("+ticketId+")");
        Ticket ticket = ticketRepository.findOne(ticketId);
        if(ticket == null){
            return TicketDeletedEvent.notFound(ticketId);
        }
        else{
            ticketRepository.delete(ticket);
            TicketDeletedEvent ticketDeletedEvent = new TicketDeletedEvent(ticketId);
            return ticketDeletedEvent;
        }
    }
    
	@Override
	public AllReadEvent readTickets(ReadAllEvent readTicketsEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Long electionId=readTicketsEvent.getParentId();
		Page <Ticket>elections=null;
		ArrayList<TicketDetails> dets=new ArrayList<TicketDetails>();
		AllReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("ElectionId "+electionId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"e.name");
		elections=ticketRepository.findByElectionId(electionId, pageable);
		if (elections!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+elections.getTotalElements()+" total pages ="+elections.getTotalPages());
			Iterator<Ticket> iter=elections.iterator();
			while (iter.hasNext())
			{
				Ticket na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getName());
				TicketDetails det=na.toTicketDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found parentId.
				Election elec=electionRepository.findOne(electionId);
				if ( (null==elec) ||
					 ((null==elec.getTitle()) || ((null==elec.getStart()) && (null==elec.getEnd()) && (null==elec.getIntroduction()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ElectionId)");
					nare=AllReadEvent.notFound(null);
				}
				else
				{	
					nare=new AllReadEvent(electionId,dets,elections.getTotalElements(),elections.getTotalPages());
				}
			}
			else
			{	
				nare=new AllReadEvent(electionId,dets,elections.getTotalElements(),elections.getTotalPages());
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
	public AllReadEvent readCandidates(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		Long ticketId=readAllEvent.getParentId();
		Page <Candidate>candidates=null;
		ArrayList<CandidateDetails> dets=new ArrayList<CandidateDetails>();
		AllReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("PositionId "+ticketId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"e.name");
		candidates=candidateRepository.findByTicketId(ticketId, pageable);
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
				Ticket ticket=ticketRepository.findOne(ticketId);
				if ( (null==ticket) ||
					 ((null==ticket.getName()) ))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ticketId)");
					nare=AllReadEvent.notFound(null);
				}
				else
				{	
					nare=new AllReadEvent(ticketId,dets,candidates.getTotalElements(),candidates.getTotalPages());
				}
			}
			else
			{	
				nare=new AllReadEvent(ticketId,dets,candidates.getTotalElements(),candidates.getTotalPages());
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
    public TicketSupportedEvent supportTicket(SupportTicketEvent supportTicketEvent) {
        TicketSupportedEvent ticketSupportedEvent;
        Ticket ticket = ticketRepository.findOne(supportTicketEvent.getTicketId());
        User user = userRepository.findByEmail(supportTicketEvent.getEmailAddress());
        if (ticket == null)
            ticketSupportedEvent = TicketSupportedEvent.entityNotFound(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
        else if (user == null)
            ticketSupportedEvent = TicketSupportedEvent.userNotFound(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
        else{
            ticketSupportedEvent = new TicketSupportedEvent(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress(), false);
//            Support supportEntity = ticketRepository.supportTicket(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
//            if(supportEntity==null)
//                ticketSupportedEvent.setResult(false);
            SupportAndNum resultRow = ticketRepository.supportTicket_return_number_of_supports(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
            Support supportEntity = resultRow.getSupport();
            Long numOfSupports = resultRow.getNumOfSupport();
            System.out.println("support:"+supportEntity);
            System.out.println("numOfSupports:"+numOfSupports);
            if(supportEntity!=null){
                ticketSupportedEvent.setResult(true);
                ticketSupportedEvent.setNumOfSupports(numOfSupports);
            }

        }
        return ticketSupportedEvent;
    }

    @Override
    public TicketSupportedEvent withdrawSupportTicket(SupportTicketEvent supportTicketEvent) {
        TicketSupportedEvent ticketSupportedEvent;
        Ticket ticket = ticketRepository.findOne(supportTicketEvent.getTicketId());
        User user = userRepository.findByEmail(supportTicketEvent.getEmailAddress());
        if (ticket == null)
            ticketSupportedEvent = TicketSupportedEvent.entityNotFound(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
        else if (user == null)
            ticketSupportedEvent = TicketSupportedEvent.userNotFound(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
        else{
            ticketRepository.withdrawSupportTicket(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
            ticketSupportedEvent = new TicketSupportedEvent(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress(), true);
        }
        return ticketSupportedEvent;
    }
}
