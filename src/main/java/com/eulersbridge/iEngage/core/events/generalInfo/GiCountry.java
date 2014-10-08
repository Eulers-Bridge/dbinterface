package com.eulersbridge.iEngage.core.events.generalInfo;

import java.util.Iterator;

public class GiCountry
{
	Long countryId;
	String countryName;
	Iterator<GiInstitution> insts;
	
	public GiCountry(Long countryId,String countryName,Iterator<GiInstitution> instIter)
	{
		this.countryId=countryId;
		this.countryName=countryName;
		this.insts=instIter;
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

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the insts
	 */
	public Iterator<GiInstitution> getInsts() {
		return insts;
	}

	/**
	 * @param insts the insts to set
	 */
	public void setInsts(Iterator<GiInstitution> insts) {
		this.insts = insts;
	}
}
