package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Yikai Gong
 */

public class CreateTicketEvent extends CreateEvent{
    public CreateTicketEvent(Details details) {
        super(details);
    }
}
