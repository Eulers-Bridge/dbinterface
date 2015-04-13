/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.rest.controller.VotingLocationController;

/**
 * @author Greg Newitt
 *
 */
public class VotingLocation extends ResourceSupport
{
	private Long votingLocationId;
	private String name;
	private String information;
	private Long ownerId;

	private static Logger LOG = LoggerFactory.getLogger(VotingLocation.class);

	/**
	 * 
	 */
	public VotingLocation()
	{
		super();
	}

	/**
	 * @param nodeId
	 * @param name
	 * @param information
	 * @param owner
	 */
	public VotingLocation(Long nodeId, String name, String information,
			Long ownerId)
	{
		super();
		this.votingLocationId = nodeId;
		this.name = name;
		this.information = information;
		this.ownerId = ownerId;
	}

	public static VotingLocation fromVotingLocationDetails(VotingLocationDetails votingLocationDetails)
    {
    	VotingLocation votingLocation = new VotingLocation();
        String simpleName=VotingLocation.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
        if (votingLocationDetails!=null)
        {
	        votingLocation.setVotingLocationId(votingLocationDetails.getNodeId());
	        votingLocation.setName(votingLocationDetails.getName());
	        votingLocation.setInformation(votingLocationDetails.getInformation());
	        votingLocation.setOwnerId(votingLocationDetails.getOwnerId());
        }
	    // {!begin selfRel}
        votingLocation.add(linkTo(VotingLocationController.class).slash(name).slash(votingLocation.votingLocationId).withSelfRel());
	    // {!end selfRel}
	    // {!begin readAll}
	    votingLocation.add(linkTo(VotingLocationController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

        return votingLocation;
    }

    public VotingLocationDetails toVotingLocationDetails()
    {
    	VotingLocationDetails photoDetails = new VotingLocationDetails(getVotingLocationId(), getName(), getInformation(), getOwnerId());
        if (LOG.isTraceEnabled()) LOG.trace("photoDetails "+photoDetails);
        return photoDetails;
    }

	public static Iterator<VotingLocation> toVotingLocationsIterator( Iterator<? extends Details> iter)
	{
		if (null==iter) return null;
		ArrayList <VotingLocation> photos=new ArrayList<VotingLocation>();
		while(iter.hasNext())
		{
			VotingLocationDetails dets=(VotingLocationDetails)iter.next();
			VotingLocation thisVotingLocation=VotingLocation.fromVotingLocationDetails(dets);
			Link self = thisVotingLocation.getLink("self");
			thisVotingLocation.removeLinks();
			thisVotingLocation.add(self);
			photos.add(thisVotingLocation);		
		}
		return photos.iterator();
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
	 * @return the votingLocationId
	 */
	public Long getVotingLocationId()
	{
		return votingLocationId;
	}

	/**
	 * @param votingLocationId the votingLocationId to set
	 */
	public void setVotingLocationId(Long votingLocationId)
	{
		this.votingLocationId = votingLocationId;
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
		if (this.votingLocationId != null)
		{
			result = prime * result + votingLocationId.hashCode();
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
		VotingLocation other = (VotingLocation) obj;
		if (votingLocationId != null)
		{
			if (votingLocationId.equals(other.votingLocationId))
				return true;
			else return false;
		}
		else
		{
			if (other.votingLocationId != null) return false;
			
			if (name == null)
			{
				if (other.name != null) return false;
			}
			else if (!name.equals(other.name)) return false;
			
			if (information == null)
			{
				if (other.information != null) return false;
			}
			else if (!information.equals(other.information)) return false;
			if (ownerId == null)
			{
				if (other.ownerId != null) return false;
			}
			else if (!ownerId.equals(other.ownerId)) return false;
		}
		return true;
	}
}

