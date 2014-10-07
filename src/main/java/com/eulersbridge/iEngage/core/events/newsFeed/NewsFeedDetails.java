/**
 * 
 */
package com.eulersbridge.iEngage.core.events.studentYear;

/**
 * @author Greg Newitt
 *
 */
public class StudentYearDetails 
{
	Long nodeId;
	String year;
	Long start;
	Long end;
	Long institutionId;

	public StudentYearDetails()
	{
		
	}
	
	public StudentYearDetails(String year, Long start, Long end, Long institutionId)
	{
		this.year=year;
		this.start=start;
		this.end=end;
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
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the start
	 */
	public Long getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Long start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Long getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Long end) {
		this.end = end;
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
		return "StudentYearDetails [nodeId=" + nodeId + ", year=" + year
				+ ", start=" + start + ", end=" + end + ", institutionId="
				+ institutionId + "]";
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof StudentYearDetails)) return false;
		StudentYearDetails dets2=(StudentYearDetails) other;
		if (dets2.getNodeId()!=null)
		{
			if (dets2.getNodeId().equals(getNodeId()))
			return true;
		}
		return false;
	}
}
