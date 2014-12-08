package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteTicketEvent extends DeleteEvent {
    public DeleteTicketEvent(Long ticketId) {
        super(ticketId);
    }
}
