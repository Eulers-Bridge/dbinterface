/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocationDetails extends Details
{
    private String name;
    private String information;
	private Long ownerId;
	
	/**
	 * 
	 */
	public VotingLocationDetails()
	{
		super();
	}

	/**
	 * @param nodeId
	 * @param name
	 * @param information
	 * @param ownerId
	 */
	public VotingLocationDetails(Long nodeId, String name, String information,
			Long ownerId)
	{
		super(nodeId);
		this.name = name;
		this.information = information;
		this.ownerId = ownerId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the information
	 */
	public String getInformation()
	{
		return information;
	}
	/**
	 * @param information the information to set
	 */
	public void setInformation(String information)
	{
		this.information = information;
	}
	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (this.nodeId != null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((information == null) ? 0 : information.hashCode());
			result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		VotingLocationDetails other = (VotingLocationDetails) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.getNodeId()))
				return true;
			else return false;
		}
		else
		{
			if (other.getNodeId() != null) return false;
			
			if (name == null)
			{
				if (other.getName() != null) return false;
			}
			else if (!name.equals(other.getName())) return false;
			
			if (information == null)
			{
				if (other.getInformation() != null) return false;
			}
			else if (!information.equals(other.getInformation())) return false;
			if (getOwnerId() == null)
			{
				if (other.getOwnerId() != null) return false;
			}
			else if (!getOwnerId().equals(other.getOwnerId())) return false;
		}
		return true;
	}
}
