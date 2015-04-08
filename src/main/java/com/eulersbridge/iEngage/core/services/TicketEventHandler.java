package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Support;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.domain.User;
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

    public TicketEventHandler(TicketRepository ticketRepository, ElectionRepository electionRepository, UserRepository userRepository)
    {
        this.ticketRepository = ticketRepository;
        this.electionRepository = electionRepository;
        this.userRepository = userRepository;
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
	public TicketsReadEvent readTickets(ReadAllEvent readTicketsEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Long electionId=readTicketsEvent.getParentId();
		Page <Ticket>elections=null;
		ArrayList<TicketDetails> dets=new ArrayList<TicketDetails>();
		TicketsReadEvent nare=null;

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
					nare=TicketsReadEvent.electionNotFound();
				}
				else
				{	
					nare=new TicketsReadEvent(electionId,dets,elections.getTotalElements(),elections.getTotalPages());
				}
			}
			else
			{	
				nare=new TicketsReadEvent(electionId,dets,elections.getTotalElements(),elections.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=TicketsReadEvent.electionNotFound();
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
            ticketSupportedEvent = new TicketSupportedEvent(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress(), true);
            Support supportEntity = ticketRepository.supportTicket(supportTicketEvent.getTicketId(), supportTicketEvent.getEmailAddress());
            if(supportEntity==null)
                ticketSupportedEvent.setResult(false);
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
