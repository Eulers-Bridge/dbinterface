/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

/**
 * @author Greg Newitt
 *
 */
public class VoteRecordDetails 
{
	private Long nodeId;
	private Long voterId;
	private Long electionId;
	private Long date;
	private String location;
	
	/**
	 * 
	 */
	public VoteRecordDetails() 
	{
		super();
	}
	
	/**
	 * @param nodeId
	 * @param voterId
	 * @param electionId
	 * @param date
	 * @param location
	 */
	public VoteRecordDetails(Long nodeId, Long voterId, Long electionId,
			Long date, String location) {
		super();
		this.nodeId = nodeId;
		this.voterId = voterId;
		this.electionId = electionId;
		this.date = date;
		this.location = location;
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
	 * @return the voterId
	 */
	public Long getVoterId() {
		return voterId;
	}

	/**
	 * @param voterId the voterId to set
	 */
	public void setVoterId(Long voterId) {
		this.voterId = voterId;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId() {
		return electionId;
	}

	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VoteRecordDetails [nodeId=" + nodeId + ", voterId=" + voterId
				+ ", electionId=" + electionId + ", date=" + date
				+ ", location=" + location + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result
					+ ((electionId == null) ? 0 : electionId.hashCode());
			result = prime * result
					+ ((location == null) ? 0 : location.hashCode());
			result = prime * result + ((voterId == null) ? 0 : voterId.hashCode());
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
		VoteRecordDetails other = (VoteRecordDetails) obj;
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
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (electionId == null) {
				if (other.electionId != null)
					return false;
			} else if (!electionId.equals(other.electionId))
				return false;
			if (location == null) {
				if (other.location != null)
					return false;
			} else if (!location.equals(other.location))
				return false;
			if (voterId == null) {
				if (other.voterId != null)
					return false;
			} else if (!voterId.equals(other.voterId))
				return false;
		}
		return true;
	}

	
}
