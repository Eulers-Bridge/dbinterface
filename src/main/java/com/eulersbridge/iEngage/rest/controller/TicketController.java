package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.core.services.TicketService;
import com.eulersbridge.iEngage.rest.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class TicketController {
    @Autowired
    TicketService ticketService;

    private static Logger LOG = LoggerFactory.getLogger(TicketController.class);

    public TicketController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.TICKET_LABEL)
    public @ResponseBody ResponseEntity<Ticket>
    createTicket(@RequestBody Ticket ticket){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create ticket "+ticket);
        CreateTicketEvent createTicketEvent = new CreateTicketEvent(ticket.toTicketDetails());
        TicketCreatedEvent ticketCreatedEvent = ticketService.createTicket(createTicketEvent);
        if(ticketCreatedEvent.getFailedId() != null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Ticket result = Ticket.fromTicketDetails((TicketDetails) ticketCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("ticket"+result.toString());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TICKET_LABEL + "/{ticketId}")
    public @ResponseBody ResponseEntity<Ticket>
    findTicket(@PathVariable Long ticketId){
        if (LOG.isInfoEnabled()) LOG.info(ticketId+" attempting to get ticket. ");
        RequestReadTicketEvent requestReadTicketEvent = new RequestReadTicketEvent(ticketId);
        ReadEvent readTicketEvent = ticketService.requestReadTicket(requestReadTicketEvent);
        if(readTicketEvent.isEntityFound()){
            Ticket ticket = Ticket.fromTicketDetails((TicketDetails) readTicketEvent.getDetails());
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.TICKET_LABEL+"/{ticketId}")
    public @ResponseBody ResponseEntity<Ticket>
    updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update ticket. " + ticketId);
        UpdatedEvent ticketUpdatedEvent = ticketService.updateTicket(new UpdateTicketEvent(ticketId, ticket.toTicketDetails()));
        if(null != ticketUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("ticketUpdatedEvent - "+ticketUpdatedEvent);
            if(ticketUpdatedEvent.isEntityFound()){
                Ticket result = Ticket.fromTicketDetails((TicketDetails) ticketUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.TICKET_LABEL+"/{ticketId}")
    public @ResponseBody ResponseEntity<Boolean>
    deleteTicket(@PathVariable Long ticketId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete ticket. " + ticketId);
        DeletedEvent ticketDeletedEvent = ticketService.deleteTicket(new DeleteTicketEvent(ticketId));
        Boolean isDeletionCompleted = Boolean.valueOf(ticketDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }
}
