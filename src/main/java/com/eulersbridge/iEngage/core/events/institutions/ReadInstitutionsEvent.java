/**
 * 
 */
package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadInstitutionsEvent extends RequestReadEvent 
{
	Long countryId=null;
	
	public ReadInstitutionsEvent()
	{
		super(null);
	}

	public ReadInstitutionsEvent(Long countryId)
	{
		super(null);
		this.countryId=countryId;
	}

	/**
	 * @return the countryId
	 */
	public Long getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
}
