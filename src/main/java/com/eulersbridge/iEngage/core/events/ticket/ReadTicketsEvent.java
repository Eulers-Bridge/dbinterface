/**
 * 
 */
package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadTicketsEvent extends RequestReadEvent
{
	Long electionId=null;
	
	public ReadTicketsEvent()
	{
		super(null);
	}

	public ReadTicketsEvent(Long electionId)
	{
		super(null);
		this.electionId=electionId;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId()
	{
		return electionId;
	}

	/**
	 * @param electionId the electionId to set
	 */
	public void setInstitutionId(Long electionId)
	{
		this.electionId = electionId;
	}

}
