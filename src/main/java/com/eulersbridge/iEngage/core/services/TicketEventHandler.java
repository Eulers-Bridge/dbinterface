package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.database.domain.Ticket;
import com.eulersbridge.iEngage.database.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class TicketEventHandler implements TicketService{
    private static Logger LOG = LoggerFactory.getLogger(TicketService.class);

    private TicketRepository ticketRepository;

    public TicketEventHandler(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketCreatedEvent createTicket(CreateTicketEvent createTicketEvent) {
        TicketDetails ticketDetails = (TicketDetails) createTicketEvent.getDetails();
        Ticket ticket = Ticket.fromTicketDetails(ticketDetails);
        Ticket result = ticketRepository.save(ticket);
        TicketCreatedEvent ticketCreatedEvent = new TicketCreatedEvent(result.getTicketId(), result.toTicketDetails());
        return ticketCreatedEvent;
    }

    @Override
    public ReadEvent requestReadTicket(RequestReadTicketEvent requestReadTicketEvent) {
        Ticket ticket = ticketRepository.findOne(requestReadTicketEvent.getNodeId());
        ReadEvent readTicketEvent;
        if(ticket != null){
            readTicketEvent = new ReadTIcketEvent(ticket.getTicketId(), ticket.toTicketDetails());
        }
        else{
            readTicketEvent = ReadTIcketEvent.notFound(requestReadTicketEvent.getNodeId());
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
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getTicketId());
            return new TicketUpdatedEvent(result.getTicketId(), result.toTicketDetails());
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
}
