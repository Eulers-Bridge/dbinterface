package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class TicketCreatedEvent extends CreatedEvent
{
    private Long failedId;

    private static Logger LOG = LoggerFactory.getLogger(TicketCreatedEvent.class);

    public TicketCreatedEvent(Long ticketId)
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.failedId = ticketId;
    }

    public TicketCreatedEvent(TicketDetails ticketDetails)
    {
        super(ticketDetails);
    }

    public Long getFailedId()
    {
        return failedId;
    }

    public void setFailedId(Long ticketId)
    {
        this.failedId = ticketId;
    }
}
