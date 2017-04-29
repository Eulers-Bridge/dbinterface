package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;
import com.eulersbridge.iEngage.security.SecurityConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface TicketService {
    @PreAuthorize("hasAnyRole('"+SecurityConstants.TICKET_MANAGER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public CreatedEvent createTicket(CreateTicketEvent createTicketEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public ReadEvent requestReadTicket(RequestReadTicketEvent requestReadTicketEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.TICKET_MANAGER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public UpdatedEvent updateTicket(UpdateTicketEvent updateTicketEvent);

    @PreAuthorize("hasAnyRole('"+SecurityConstants.TICKET_MANAGER_ROLE+"','"+SecurityConstants.ADMIN_ROLE+"')")
    public DeletedEvent deleteTicket(DeleteTicketEvent deleteTicketEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readTickets(ReadAllEvent readTicketsEvent,
			Direction sortDirection, int pageNumber, int pageLength);

	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or (hasRole('"+SecurityConstants.USER_ROLE+"') and #supportTicketEvent.getEmailAddress()==authentication.name)")
    public TicketSupportedEvent supportTicket(SupportTicketEvent supportTicketEvent);

	@PreAuthorize("hasRole('"+SecurityConstants.ADMIN_ROLE+"') or (hasRole('"+SecurityConstants.USER_ROLE+"') and #supportTicketEvent.getEmailAddress()==authentication.name)")
    public TicketSupportedEvent withdrawSupportTicket(SupportTicketEvent supportTicketEvent);

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
    public LikeableObjectLikesEvent findSupporters(LikesLikeableObjectEvent likesLikeableObjectEvent, Direction sortDirection, int pageNumber, int pageSize );

    @PreAuthorize("hasRole('"+SecurityConstants.USER_ROLE+"')")
	public AllReadEvent readCandidates(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
