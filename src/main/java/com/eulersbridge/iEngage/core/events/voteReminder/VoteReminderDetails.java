/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class VoteReminderDetails extends Details
{
	private String userEmail;
	private Long electionId;
	private Long date;
	private String location;
	private Long timestamp;

	/**
	 * 
	 */
	public VoteReminderDetails() 
	{
		super();
	}
	
	/**
	 * @param nodeId
	 * @param userEmail
	 * @param electionId
	 * @param date
	 * @param location
	 * @param timestamp
	 */
	public VoteReminderDetails(Long nodeId, String userEmail, Long electionId,
			Long date, String location, Long timestamp)
	{
		super(nodeId);
		this.userEmail = userEmail;
		this.electionId = electionId;
		this.date = date;
		this.location = location;
		this.timestamp = timestamp;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "VoteReminderDetails [nodeId=" + nodeId + ", userId=" + userEmail
				+ ", electionId=" + electionId + ", date=" + date
				+ ", location=" + location + ", timestamp=" + timestamp + "]";
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
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result
					+ ((electionId == null) ? 0 : electionId.hashCode());
			result = prime * result
					+ ((location == null) ? 0 : location.hashCode());
			result = prime * result
					+ ((timestamp == null) ? 0 : timestamp.hashCode());
			result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
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
		VoteReminderDetails other = (VoteReminderDetails) obj;
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
			if (timestamp == null) {
				if (other.timestamp != null)
					return false;
			} else if (!timestamp.equals(other.timestamp))
				return false;
			if (userEmail == null) {
				if (other.userEmail != null)
					return false;
			} else if (!userEmail.equals(other.userEmail))
				return false;
		}
		return true;
	}

}
