/**
 * 
 */
package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class TicketsReadEvent extends AllReadEvent
{
	Iterable<TicketDetails> tickets;
	private boolean electionFound=true;
	
	public TicketsReadEvent(Long electionId, Iterable<TicketDetails> tickets, Long totalItems, Integer totalPages)
	{
		super(electionId,totalItems,totalPages);
		this.tickets=tickets;
	}

	public TicketsReadEvent(Long electionId, Iterable<TicketDetails> tickets)
	{
		super(electionId);
		this.tickets=tickets;
	}

	public TicketsReadEvent(Iterable<TicketDetails> tickets)
	{
		super(null);
		this.tickets=tickets;
	}

	public TicketsReadEvent()
	{
		super(null);
	}
	
	/**
	 * @return the tickets
	 */
	public Iterable<TicketDetails> getTickets() 
	{
		return tickets;
	}

	/**
	 * @param tickets the tickets to set
	 */
	public void setTickets(Iterable<TicketDetails> tickets) 
	{
		this.tickets = tickets;
	}

	/**
	 * @return the electionFound
	 */
	public boolean isElectionFound() {
		return electionFound;
	}

	public static TicketsReadEvent electionNotFound() 
	{
		TicketsReadEvent nare=new TicketsReadEvent();
		nare.electionFound=false;
		nare.entityFound=false;
		return nare;
	}

}
