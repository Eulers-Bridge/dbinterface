package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.Details;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionDetails extends Details
{
    private String name;
    private String description;
    private Long electionId;

    private static Logger LOG = LoggerFactory.getLogger(PositionDetails.class);

    /**
	 * 
	 */
	public PositionDetails()
	{
		super();
	}

	/**
	 * @param name
	 * @param description
	 * @param electionId
	 */
	public PositionDetails(Long nodeId,String name, String description, Long electionId)
	{
		super(nodeId);
		this.name = name;
		this.description = description;
		this.electionId = electionId;
	}

	@Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", description = ");
        buff.append(getDescription());
        buff.append(", electionId = ");
        buff.append(getElectionId());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        if (getNodeId()!=null)
        {
            result = prime * result	+ getNodeId().hashCode();
        }
        else
        {
            result = prime * result	+ ((name == null) ? 0 : name.hashCode());
            result = prime * result	+ ((description == null) ? 0 : description.hashCode());
            result = prime * result + ((electionId == null) ? 0 : electionId.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PositionDetails other = (PositionDetails) obj;
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
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (description == null) {
                if (other.description != null)
                    return false;
            } else if (!description.equals(other.description))
                return false;
            if (electionId == null) {
                if (other.electionId != null)
                    return false;
            } else if (!electionId.equals(other.electionId))
                return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId)
    {
    	this.electionId = electionId;
    }
}
