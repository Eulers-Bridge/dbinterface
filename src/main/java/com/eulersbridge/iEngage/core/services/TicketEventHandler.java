package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.CreateTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.DeleteTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.ReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.ReadTicketsEvent;
import com.eulersbridge.iEngage.core.events.ticket.RequestReadTicketEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketCreatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDeletedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.core.events.ticket.TicketUpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.TicketsReadEvent;
import com.eulersbridge.iEngage.core.events.ticket.UpdateTicketEvent;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.TicketRepository;

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

    public TicketEventHandler(TicketRepository ticketRepository, ElectionRepository electionRepository)
    {
        this.ticketRepository = ticketRepository;
        this.electionRepository = electionRepository;
    }

    @Override
    public CreatedEvent createTicket(CreateTicketEvent createTicketEvent) 
    {
        TicketDetails ticketDetails = (TicketDetails) createTicketEvent.getDetails();
    	Long electionId=ticketDetails.getElectionId();
        
    	if (LOG.isDebugEnabled()) LOG.debug("Finding election with nodeId = "+electionId);
    	Election elect=electionRepository.findOne(electionId);

    	TicketCreatedEvent ticketCreatedEvent;
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
	public TicketsReadEvent readTickets(ReadTicketsEvent readTicketsEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Long electionId=readTicketsEvent.getParentId();
		Page <Ticket>elections=null;
		ArrayList<TicketDetails> dets=new ArrayList<TicketDetails>();
		TicketsReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("ElectionId "+electionId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"e.name");
		elections=ticketRepository.findByElectionId(electionId, pageable);
		if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+elections.getTotalElements()+" total pages ="+elections.getTotalPages());
		if (elections!=null)
		{
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
					nare=new TicketsReadEvent(electionId,dets);
				}
			}
			else
			{	
				nare=new TicketsReadEvent(electionId,dets);
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=TicketsReadEvent.electionNotFound();
		}
		return nare;
	}

}
