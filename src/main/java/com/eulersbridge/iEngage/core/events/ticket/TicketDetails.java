package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.core.events.Details;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Yikai Gong
 */

public class TicketDetails extends Details
{
	private String name;
	private String logo;
	private Set<String> pictures;
	private String information;
	private String colour;
    private Iterable<String> candidateNames;
	private Long electionId;
    private String chararcterCode;
    private Integer NumberOfSupporters;

	private static Logger LOG = LoggerFactory.getLogger(TicketDetails.class);

	@Override
	public String toString()
	{
		StringBuffer buff = new StringBuffer("[ id = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", name = ");
		buff.append(getName());
		buff.append(", logo = ");
		buff.append(getLogo());
		buff.append(", pictures = ");
		buff.append(getPictures());
		buff.append(", information = ");
		buff.append(getInformation());
		buff.append(", colour = ");
		buff.append(getColour());
		buff.append(", candidateNames = ");
		buff.append(getCandidateNames());
		buff.append(", electionId = ");
		buff.append(getElectionId());
        buff.append(", characterCode = ");
        buff.append(getChararcterCode());
        buff.append(", numberOfSupporters = ");
        buff.append(getNumberOfSupporters());
		buff.append(" ]");
		retValue = buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
		return retValue;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (getNodeId() != null)
		{
			result = prime * result + getNodeId().hashCode();
		}
		else
		{
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((logo == null) ? 0 : logo.hashCode());
			result = prime * result
					+ ((pictures == null) ? 0 : pictures.hashCode());
			result = prime * result
					+ ((information == null) ? 0 : information.hashCode());
			result = prime * result
					+ ((colour == null) ? 0 : colour.hashCode());
			result = prime * result
					+ ((candidateNames == null) ? 0 : candidateNames.hashCode());
			result = prime * result
					+ ((electionId == null) ? 0 : electionId.hashCode());
		}
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TicketDetails other = (TicketDetails) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null) return false;
			if (name == null)
			{
				if (other.name != null) return false;
			}
			else if (!name.equals(other.name)) return false;
			if (electionId == null)
			{
				if (other.electionId != null) return false;
			}
			else if (!electionId.equals(other.electionId)) return false;
			if (information == null)
			{
				if (other.information != null) return false;
			}
			else if (!information.equals(other.information)) return false;
			if (colour == null)
				{
					if (other.colour != null) return false;
				}
			else if (!colour.equals(other.colour)) return false;
			if (candidateNames == null)
			{
				if (other.candidateNames != null) return false;
			}
			else if (!candidateNames.equals(other.candidateNames)) return false;
		}
		return true;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	public Set<String> getPictures()
	{
		return pictures;
	}

	public void setPictures(Set<String> pictures)
	{
		this.pictures = pictures;
	}

	public String getInformation()
	{
		return information;
	}

	public void setInformation(String information)
	{
		this.information = information;
	}

    public String getChararcterCode() {
        return chararcterCode;
    }

    public void setChararcterCode(String chararcterCode) {
        this.chararcterCode = chararcterCode;
    }

    public Iterable<String> getCandidateNames() {
        return candidateNames;
    }

    public void setCandidateNames(Iterable<String> candidateNames) {
        this.candidateNames = candidateNames;
    }

    public Integer getNumberOfSupporters() {
        return NumberOfSupporters;
    }

    public void setNumberOfSupporters(Integer numberOfSupporters) {
        NumberOfSupporters = numberOfSupporters;
    }

    /**
	 * @return the colour
	 */
	public String getColour()
	{
		return colour;
	}

	/**
	 * @param colour
	 *            the colour to set
	 */
	public void setColour(String colour)
	{
		this.colour = colour;
	}

	/**
	 * @return the electionId
	 */
	public Long getElectionId()
	{
		return electionId;
	}

	/**
	 * @param electionId
	 *            the electionId to set
	 */
	public void setElectionId(Long electionId)
	{
		this.electionId = electionId;
	}
}
