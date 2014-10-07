/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

/**
 * @author Greg Newitt
 *
 */
public class NewsFeedDetails 
{
	Long nodeId;
	Long institutionId;

	public NewsFeedDetails()
	{
		
	}
	
	public NewsFeedDetails(Long institutionId)
	{
		this.institutionId=institutionId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
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
	
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof NewsFeedDetails)) return false;
		NewsFeedDetails dets2=(NewsFeedDetails) other;
		if (dets2.getNodeId()!=null)
		{
			if (dets2.getNodeId().equals(getNodeId()))
			return true;
		}
		return false;
	}
}
