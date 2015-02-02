package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadTicketEvent extends ReadEvent{
    public ReadTicketEvent(Long ticketId) {
        super(ticketId);
    }

    public ReadTicketEvent(Long ticketId, TicketDetails ticketDetails) {
        super(ticketId, ticketDetails);
    }
}
