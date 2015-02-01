package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadTIcketEvent extends ReadEvent{
    public ReadTIcketEvent(Long ticketId) {
        super(ticketId);
    }

    public ReadTIcketEvent(Long ticketId, TicketDetails ticketDetails) {
        super(ticketId, ticketDetails);
    }
}
