package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class TicketDeletedEvent extends DeletedEvent{
    public TicketDeletedEvent(Long ticketId) {
        super(ticketId);
    }
}
