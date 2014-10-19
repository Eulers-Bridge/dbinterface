/**
 * 
 */
package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;

/**
 * @author Greg Newitt
 *
 */
public class ElectionsReadEvent extends ReadEvent 
{
	Iterable<ElectionDetails> elections;
	Long institutionId=null;
	private boolean institutionFound=true;
	
	public ElectionsReadEvent(Long institutionId, Iterable<ElectionDetails> elections)
	{
		this.elections=elections;
		this.institutionId=institutionId;
	}

	public ElectionsReadEvent(Iterable<ElectionDetails> elections)
	{
		this.elections=elections;
	}

	public ElectionsReadEvent()
	{
		super();
	}
	
	/**
	 * @return the elections
	 */
	public Iterable<ElectionDetails> getElections() 
	{
		return elections;
	}

	/**
	 * @param elections the elections to set
	 */
	public void setElections(Iterable<ElectionDetails> elections) 
	{
		this.elections = elections;
	}

	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound() {
		return institutionFound;
	}

	public static ElectionsReadEvent institutionNotFound() 
	{
		ElectionsReadEvent nare=new ElectionsReadEvent();
		nare.institutionFound=false;
		nare.entityFound=false;
		return nare;
	}
}
