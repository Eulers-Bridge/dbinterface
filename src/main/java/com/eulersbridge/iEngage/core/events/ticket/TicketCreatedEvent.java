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
    private boolean electionFound=true;

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

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound()
	{
		return electionFound;
	}

	/**
	 * @param electionFound the electionFound to set
	 */
	public void setElectionFound(boolean electionFound)
	{
		this.electionFound = electionFound;
	}

	public static TicketCreatedEvent electionNotFound(Long electionId)
	{
		TicketCreatedEvent evt=new TicketCreatedEvent(electionId);
		evt.setElectionFound(false);
		evt.setFailed(true);
		return evt;
	}
}
