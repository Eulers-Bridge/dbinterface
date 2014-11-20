package com.eulersbridge.iEngage.core.events.institutions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.Details;

public class InstitutionDetails extends Details
{
	private	Long nodeId;
	private String name;
	private String campus;
	private String state;
	private String countryName;
	
    private static Logger LOG = LoggerFactory.getLogger(InstitutionDetails.class);

	public InstitutionDetails(Long id) 
	{
		this.nodeId=id;
	}
	public Long getInstitutionId() {
		return nodeId;
	}
	public void setInstitutionId(Long institutionId) {
		this.nodeId = institutionId;
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
		buff.append(getInstitutionId());
		buff.append(", name = ");
		buff.append(getName());
		buff.append(", campus = ");
		buff.append(getCampus());
		buff.append(", state = ");
		buff.append(getState());
		buff.append(", countryName = ");
		buff.append(getCountryName());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		
		if (nodeId!=null)
		{
			result = prime * result	+ nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((campus == null) ? 0 : campus.hashCode());
			result = prime * result
					+ ((countryName == null) ? 0 : countryName.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((state == null) ? 0 : state.hashCode());
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstitutionDetails other = (InstitutionDetails) obj;
		if (nodeId!=null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId!=null)
				return false;
			if (campus == null) {
				if (other.campus != null)
					return false;
			} else if (!campus.equals(other.campus))
				return false;
			if (countryName == null) {
				if (other.countryName != null)
					return false;
			} else if (!countryName.equals(other.countryName))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (state == null) {
				if (other.state != null)
					return false;
			} else if (!state.equals(other.state))
				return false;
		}
		return true;
	}
}
