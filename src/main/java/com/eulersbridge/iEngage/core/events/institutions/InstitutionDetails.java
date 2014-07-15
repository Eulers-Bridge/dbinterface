package com.eulersbridge.iEngage.core.events.institutions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstitutionDetails 
{
	private	Long institutionId;
	private String name;
	private String campus;
	private String state;
	private String country;
	
    private static Logger LOG = LoggerFactory.getLogger(InstitutionDetails.class);

	public InstitutionDetails(Long id) 
	{
		this.institutionId=id;
	}
	public Long getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ id = ");
		String retValue;
		buff.append(getInstitutionId());
		buff.append(", name = ");
		buff.append(getName());
		buff.append(", campus = ");
		buff.append(getCampus());
		buff.append(", state = ");
		buff.append(getState());
		buff.append(", country = ");
		buff.append(getCountry());
		buff.append(", yearOfBirth = ");
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}

}
