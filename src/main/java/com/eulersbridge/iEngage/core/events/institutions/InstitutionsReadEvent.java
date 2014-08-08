/**
 * 
 */
package com.eulersbridge.iEngage.core.events.institutions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class InstitutionsReadEvent extends ReadEvent 
{
	Iterable<InstitutionDetails> institutions;
	Long countryId=null;
	
	public InstitutionsReadEvent(Long countryId, Iterable<InstitutionDetails> institutions)
	{
		this.institutions=institutions;
		this.countryId=countryId;
	}

	public InstitutionsReadEvent(Iterable<InstitutionDetails> institutions)
	{
		this.institutions=institutions;
	}

	/**
	 * @return the institutions
	 */
	public Iterable<InstitutionDetails> getInstitutions() 
	{
		return institutions;
	}

	/**
	 * @param countrys the institutions to set
	 */
	public void setInstitutions(Iterable<InstitutionDetails> institutions) 
	{
		this.institutions = institutions;
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
