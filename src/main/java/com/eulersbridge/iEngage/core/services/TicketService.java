package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.ticket.*;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */

public interface TicketService {
    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public CreatedEvent createTicket(CreateTicketEvent createTicketEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
    public ReadEvent requestReadTicket(RequestReadTicketEvent requestReadTicketEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public UpdatedEvent updateTicket(UpdateTicketEvent updateTicketEvent);

    @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
    public DeletedEvent deleteTicket(DeleteTicketEvent deleteTicketEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public AllReadEvent readTickets(ReadAllEvent readTicketsEvent,
			Direction sortDirection, int pageNumber, int pageLength);

	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #supportTicketEvent.getEmailAddress()==authentication.name)")
    public TicketSupportedEvent supportTicket(SupportTicketEvent supportTicketEvent);

	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #supportTicketEvent.getEmailAddress()==authentication.name)")
    public TicketSupportedEvent withdrawSupportTicket(SupportTicketEvent supportTicketEvent);

    @PreAuthorize("hasRole('ROLE_USER')")
	public AllReadEvent readCandidates(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength);
}
