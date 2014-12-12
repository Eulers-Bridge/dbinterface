package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class TicketUpdatedEvent extends UpdatedEvent{
    public TicketUpdatedEvent(Long ticketId, TicketDetails ticketDetails) {
        super(ticketId, ticketDetails);
    }

    public TicketUpdatedEvent(Long ticketId) {
        super(ticketId);
    }
}
