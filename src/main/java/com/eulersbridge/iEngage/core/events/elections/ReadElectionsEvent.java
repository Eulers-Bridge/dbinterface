/**
 * 
 */
package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadElectionsEvent extends RequestReadEvent 
{
	Long institutionId=null;
	
	public ReadElectionsEvent()
	{
		super(null);
	}

	public ReadElectionsEvent(Long institutionId)
	{
		super(null);
		this.institutionId=institutionId;
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
}
