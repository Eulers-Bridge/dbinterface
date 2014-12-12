package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateTicketEvent extends UpdateEvent {
    public UpdateTicketEvent(Long ticketId, TicketDetails ticketDetails) {
        super(ticketId, ticketDetails);
    }
}
