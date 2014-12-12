package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadTicketEvent extends RequestReadEvent {
    public RequestReadTicketEvent(Long ticketId) {
        super(ticketId);
    }
}
