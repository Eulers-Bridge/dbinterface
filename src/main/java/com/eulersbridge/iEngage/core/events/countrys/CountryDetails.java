package com.eulersbridge.iEngage.core.events.countrys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryDetails 
{
	private	Long countryId;
	private String countryName;
	
    private static Logger LOG = LoggerFactory.getLogger(CountryDetails.class);

	public CountryDetails(Long id) 
	{
		this.countryId=id;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ id = ");
		String retValue;
		buff.append(getCountryId());
		buff.append(", countryName = ");
		buff.append(getCountryName());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof CountryDetails)) return false;
		CountryDetails dets2=(CountryDetails) other;
		if (dets2.getCountryId()!=null)
		{
			if (dets2.getCountryId().equals(getCountryId()))
				return true;
		}
		return false;
	}
}
