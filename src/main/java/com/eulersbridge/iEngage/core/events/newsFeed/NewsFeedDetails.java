/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class NewsFeedDetails extends Details
{
	Long institutionId;

	public NewsFeedDetails()
	{
		
	}
	
	public NewsFeedDetails(Long institutionId)
	{
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "StudentYearDetails [nodeId=" + nodeId + ", institutionId="
				+ institutionId + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (null!=nodeId)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result
					+ ((institutionId == null) ? 0 : institutionId.hashCode());
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
		NewsFeedDetails other = (NewsFeedDetails) obj;
		if (nodeId != null) 
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		} 
		else
		{
			if (other.nodeId != null)
				return false;
			if (institutionId == null) {
				if (other.institutionId != null)
					return false;
			} else if (!institutionId.equals(other.institutionId))
				return false;
		}
		return true;
	}
}
