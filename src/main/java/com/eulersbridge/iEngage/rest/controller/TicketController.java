package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.core.services.TicketService;
import com.eulersbridge.iEngage.rest.domain.FindsParent;
import com.eulersbridge.iEngage.rest.domain.Ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
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
        ResponseEntity<Ticket> response;       
        CreatedEvent ticketCreatedEvent=null;
        
        if (ticket.getElectionId()!=null)
	        ticketCreatedEvent = ticketService.createTicket(createTicketEvent);
        if(null==ticketCreatedEvent)
        {
            response = new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
        }
    	else if ((ticketCreatedEvent.getClass()==TicketCreatedEvent.class)&&(!((TicketCreatedEvent)ticketCreatedEvent).isElectionFound()))
    	{
    		response = new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
    	}
    	else if((null==ticketCreatedEvent.getNodeId())||(ticketCreatedEvent.isFailed()))
        {
            response = new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Ticket result = Ticket.fromTicketDetails((TicketDetails) ticketCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("ticket"+result.toString());
            response = new ResponseEntity<Ticket>(result, HttpStatus.CREATED);
        }
        return response;
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

	/**
	 * Is passed all the necessary data to read tickets from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the tickets read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the ticket objects to be read.
	 * @return the tickets.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TICKETS_LABEL
			+ "/{electionId}")
	public @ResponseBody ResponseEntity<FindsParent> findTickets(
			@PathVariable(value = "") Long electionId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		ResponseEntity<FindsParent> response;
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve tickets from institution "
					+ electionId + '.');

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent ticketsEvent = ticketService.readTickets(
				new ReadAllEvent(electionId), sortDirection,
				pageNumber, pageLength);

		if (!ticketsEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Ticket> tickets = Ticket
					.toTicketsIterator(ticketsEvent.getDetails().iterator());
			FindsParent theTickets = FindsParent.fromArticlesIterator(tickets, ticketsEvent.getTotalItems(), ticketsEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(theTickets, HttpStatus.OK);
		}
		return response;
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
        ResponseEntity<Boolean> response;

        DeletedEvent ticketDeletedEvent = ticketService.deleteTicket(new DeleteTicketEvent(ticketId));
        Boolean isDeletionCompleted = Boolean.valueOf(ticketDeletedEvent.isDeletionCompleted());
    	if (isDeletionCompleted)
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.OK);
    	else if (ticketDeletedEvent.isEntityFound())
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.GONE);
    	else
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.NOT_FOUND);
    	return response;
    }

    //Support
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.TICKET_LABEL+"/{ticketId}"+ ControllerConstants.SUPPORT +"/{email}")
    public @ResponseBody ResponseEntity<Boolean> supportTicket(@PathVariable Long ticketId, @PathVariable String email){
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to have " + email + " support ticket: " + ticketId);
        TicketSupportedEvent ticketSupportedEvent = ticketService.supportTicket(new SupportTicketEvent(ticketId, email));
        ResponseEntity<Boolean> response;
        if (!ticketSupportedEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!ticketSupportedEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restEvent = ticketSupportedEvent.isResult();
            response = new ResponseEntity<Boolean>(restEvent, HttpStatus.OK);
        }
        return response;
    }

    //Withdraw Support
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.TICKET_LABEL+"/{ticketId}"+ ControllerConstants.SUPPORT +"/{email}")
    public @ResponseBody ResponseEntity<Boolean> withdrawSupportTicket(@PathVariable Long ticketId, @PathVariable String email){
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to have " + email + "withdraw support ticket: " + ticketId);
        TicketSupportedEvent ticketSupportedEvent = ticketService.withdrawSupportTicket(new SupportTicketEvent(ticketId, email));
        ResponseEntity<Boolean> response;
        if (!ticketSupportedEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!ticketSupportedEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restEvent = ticketSupportedEvent.isResult();
            response = new ResponseEntity<Boolean>(restEvent, HttpStatus.OK);
        }
        return response;
    }
}
