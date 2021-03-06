package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.core.services.interfacePack.TicketService;
import com.eulersbridge.iEngage.rest.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

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
  public
  @ResponseBody
  ResponseEntity<Ticket>
  createTicket(@RequestBody Ticket ticket) {
    if (LOG.isInfoEnabled()) LOG.info("attempting to create ticket " + ticket);
    CreateTicketEvent createTicketEvent = new CreateTicketEvent(ticket.toTicketDetails());
    ResponseEntity<Ticket> response;
    CreatedEvent ticketCreatedEvent = null;

    if (ticket.getElectionId() != null)
      ticketCreatedEvent = ticketService.createTicket(createTicketEvent);
    if (null == ticketCreatedEvent) {
      response = new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
    } else if ((ticketCreatedEvent.getClass() == TicketCreatedEvent.class) && (!((TicketCreatedEvent) ticketCreatedEvent).isElectionFound())) {
      response = new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
    } else if ((null == ticketCreatedEvent.getNodeId()) || (ticketCreatedEvent.isFailed())) {
      response = new ResponseEntity<Ticket>(HttpStatus.BAD_REQUEST);
    } else {
      Ticket result = Ticket.fromTicketDetails((TicketDetails) ticketCreatedEvent.getDetails());
      if (LOG.isDebugEnabled()) LOG.debug("ticket" + result.toString());
      response = new ResponseEntity<Ticket>(result, HttpStatus.CREATED);
    }
    return response;
  }

  //Get
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TICKET_LABEL + "/{ticketId}")
  public
  @ResponseBody
  ResponseEntity<Ticket>
  findTicket(@PathVariable Long ticketId) {
    if (LOG.isInfoEnabled()) LOG.info(ticketId + " attempting to get ticket. ");
    RequestReadTicketEvent requestReadTicketEvent = new RequestReadTicketEvent(ticketId);
    ReadEvent readTicketEvent = ticketService.requestReadTicket(requestReadTicketEvent);
    if (readTicketEvent.isEntityFound()) {
      Ticket ticket = Ticket.fromTicketDetails((TicketDetails) readTicketEvent.getDetails());
      return new ResponseEntity<>(ticket, HttpStatus.OK);
    } else {
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
   * @param electionId the electionId of the ticket objects to be read.
   * @return the tickets.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TICKETS_LABEL
    + "/{electionId}")
  public
  @ResponseBody
  ResponseEntity<WrappedDomainList> findTickets(
    @PathVariable(value = "") Long electionId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    ResponseEntity<WrappedDomainList> response;

    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve tickets from institution "
        + electionId + '.');

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent ticketsEvent = ticketService.readTickets(
      new ReadAllEvent(electionId), sortDirection,
      pageNumber, pageLength);

    if (!ticketsEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<Ticket> tickets = Ticket
        .toTicketsIterator(ticketsEvent.getDetails().iterator());
      WrappedDomainList theTickets = WrappedDomainList.fromIterator(tickets, ticketsEvent.getTotalItems(), ticketsEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theTickets, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Is passed all the necessary data to read candidates for a ticket from the database. The
   * request must be a GET with the positionId presented as the final
   * portion of the URL.
   * <p/>
   * This method will return the candidates read from the database.
   *
   * @param ticketId the ticketId of the candidate objects to be read.
   * @return the candidates.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TICKET_LABEL
    + "/{ticketId}" + ControllerConstants.CANDIDATES_LABEL)
  public
  @ResponseBody
  ResponseEntity<WrappedDomainList> findCandidatesForPosition(
    @PathVariable(value = "") Long ticketId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve candidates for a ticket "
        + ticketId + '.');
    ResponseEntity<WrappedDomainList> response;

    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    AllReadEvent candidatesEvent = ticketService.readCandidates(
      new ReadAllEvent(ticketId), sortDirection,
      pageNumber, pageLength);

    if (!candidatesEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<CandidateDomain> candidates = CandidateDomain
        .toCandidatesIterator(candidatesEvent.getDetails().iterator());
      WrappedDomainList theCandidates = WrappedDomainList.fromIterator(candidates, candidatesEvent.getTotalItems(), candidatesEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theCandidates, HttpStatus.OK);
    }
    return response;
  }

  //Update
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.TICKET_LABEL + "/{ticketId}")
  public
  @ResponseBody
  ResponseEntity<Ticket>
  updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to update ticket. " + ticketId);
    UpdatedEvent ticketUpdatedEvent = ticketService.updateTicket(new UpdateTicketEvent(ticketId, ticket.toTicketDetails()));
    if (null != ticketUpdatedEvent) {
      if (LOG.isDebugEnabled())
        LOG.debug("ticketUpdatedEvent - " + ticketUpdatedEvent);
      if (ticketUpdatedEvent.isEntityFound()) {
        Ticket result = Ticket.fromTicketDetails((TicketDetails) ticketUpdatedEvent.getDetails());
        if (LOG.isDebugEnabled()) LOG.debug("result = " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  //Delete
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.TICKET_LABEL + "/{ticketId}")
  public
  @ResponseBody
  ResponseEntity<Response>
  deleteTicket(@PathVariable Long ticketId) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete ticket. " + ticketId);
    ResponseEntity<Response> response;

    DeletedEvent ticketDeletedEvent = ticketService.deleteTicket(new DeleteTicketEvent(ticketId));
    Response restEvent;
    if (!ticketDeletedEvent.isEntityFound()) {
      restEvent = Response.failed("Not found");
      response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
    } else {
      if (ticketDeletedEvent.isDeletionCompleted()) {
        restEvent = new Response();
        response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      } else {
        restEvent = Response.failed("Could not delete");
        response = new ResponseEntity<Response>(restEvent, HttpStatus.GONE);
      }
    }
    return response;
  }

  //Support
  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.TICKET_LABEL + "/{ticketId}" + ControllerConstants.SUPPORT + "/{email}")
  public
  @ResponseBody
  ResponseEntity<Response> supportTicket(@PathVariable Long ticketId, @PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to have " + email + " support ticket: " + ticketId);
    TicketSupportedEvent ticketSupportedEvent = ticketService.supportTicket(new SupportTicketEvent(ticketId, email));
    ResponseEntity<Response> response;
    if (!ticketSupportedEvent.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.GONE);
    } else if (!ticketSupportedEvent.isUserFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
//            Boolean restEvent = ticketSupportedEvent.isResult();
      Response restEvent = new Response(ticketSupportedEvent.isResult());
      restEvent.setResponseObject(ticketSupportedEvent.getNumOfSupports());
      response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
    }
    return response;
  }

  //Withdraw Support
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.TICKET_LABEL + "/{ticketId}" + ControllerConstants.SUPPORT + "/{email}")
  public
  @ResponseBody
  ResponseEntity<Response> withdrawSupportTicket(@PathVariable Long ticketId, @PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to have " + email + "withdraw support ticket: " + ticketId);
    TicketSupportedEvent ticketSupportedEvent = ticketService.withdrawSupportTicket(new SupportTicketEvent(ticketId, email));
    ResponseEntity<Response> response;
    if (!ticketSupportedEvent.isEntityFound()) {
      response = new ResponseEntity<Response>(HttpStatus.GONE);
    } else if (!ticketSupportedEvent.isUserFound()) {
      response = new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
    } else {
      Response restEvent;
      if (ticketSupportedEvent.isResult()) {
        restEvent = new Response();
        response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      } else {
        restEvent = Response.failed("Could not delete");
        response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
      }
    }
    return response;
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.TICKET_LABEL + "/{ticketId}" + ControllerConstants.SUPPORTERS)
  public
  @ResponseBody
  ResponseEntity<Iterator<LikeInfo>> findSupporters(
    @PathVariable Long ticketId,
    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve supporters from ticket " + ticketId + '.');
    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;

    LikeableObjectLikesEvent likeableObjectLikesEvent = ticketService.findSupporters(new LikesLikeableObjectEvent(ticketId), sortDirection, pageNumber, pageLength);

    Iterator<LikeInfo> supporters = UserDomain.toLikesIterator(likeableObjectLikesEvent.getUserDetails().iterator());
    if (!supporters.hasNext()) {
      ReadEvent readTicketEvent = ticketService.requestReadTicket(new RequestReadTicketEvent(ticketId));
      if (!readTicketEvent.isEntityFound())
        return new ResponseEntity<Iterator<LikeInfo>>(HttpStatus.NOT_FOUND);
      else
        return new ResponseEntity<Iterator<LikeInfo>>(supporters, HttpStatus.OK);
    } else
      return new ResponseEntity<Iterator<LikeInfo>>(supporters, HttpStatus.OK);
  }
}
