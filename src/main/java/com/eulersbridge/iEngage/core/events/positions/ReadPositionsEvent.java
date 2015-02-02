/**
 * 
 */
package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadPositionsEvent extends RequestReadEvent
{
	Long electionId=null;
	
	public ReadPositionsEvent()
	{
		super(null);
	}

	public ReadPositionsEvent(Long electionId)
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
