/**
 * 
 */
package com.eulersbridge.iEngage.core.events.task;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadTasksEvent extends RequestReadEvent
{
	Long electionId=null;
	
	public ReadTasksEvent()
	{
		super(null);
	}

	public ReadTasksEvent(Long electionId)
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
