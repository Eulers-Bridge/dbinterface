/**
 * 
 */
package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class TicketsReadEvent extends ReadEvent
{
	Iterable<TicketDetails> tickets;
	Long electionId=null;
	private boolean electionFound=true;
	
	public TicketsReadEvent(Long electionId, Iterable<TicketDetails> elections)
	{
		super(1l);
		this.tickets=elections;
		this.electionId=electionId;
	}

	public TicketsReadEvent(Iterable<TicketDetails> elections)
	{
		super(1l);
		this.tickets=elections;
	}

	public TicketsReadEvent()
	{
		super(1l);
	}
	
	/**
	 * @return the elections
	 */
	public Iterable<TicketDetails> getTickets() 
	{
		return tickets;
	}

	/**
	 * @param elections the elections to set
	 */
	public void setTickets(Iterable<TicketDetails> tickets) 
	{
		this.tickets = tickets;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId() {
		return electionId;
	}

	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId) {
		this.electionId = electionId;
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
