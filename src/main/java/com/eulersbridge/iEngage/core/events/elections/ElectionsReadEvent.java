/**
 * 
 */
package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.AllReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ElectionsReadEvent extends AllReadEvent 
{
	Iterable<ElectionDetails> elections;
	Long institutionId=null;
	private boolean institutionFound=true;
	
	public ElectionsReadEvent(Long institutionId,
			Iterable<ElectionDetails> dets, Long totalItems, Integer totalPages)
	{
		super(institutionId, totalItems, totalPages);
		this.elections=dets;
		
	}

	public ElectionsReadEvent(Long institutionId, Iterable<ElectionDetails> elections)
	{
		super(institutionId);
		this.elections=elections;
	}

	public ElectionsReadEvent(Iterable<ElectionDetails> elections)
	{
		super(null);
		this.elections=elections;
	}

	public ElectionsReadEvent()
	{
		super(null);
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
		return getParentId();
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		setParentId(institutionId);
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
