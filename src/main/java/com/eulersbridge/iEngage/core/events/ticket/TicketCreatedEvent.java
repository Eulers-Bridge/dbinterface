package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class TicketCreatedEvent extends CreatedEvent{
    private Long ticketId;

    private static Logger LOG = LoggerFactory.getLogger(TicketCreatedEvent.class);

    public TicketCreatedEvent(Long ticketId) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.ticketId = ticketId;
    }

    public TicketCreatedEvent(Long ticketId, TicketDetails ticketDetails) {
        super(ticketDetails);
        this.ticketId = ticketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
