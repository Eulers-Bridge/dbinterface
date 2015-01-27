package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.positions.PositionDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Position {
    @GraphId
    private Long nodeId;
    private String name;
    private String description;
    @RelatedTo(type = DatabaseDomainConstants.HAS_POSITION_LABEL, direction = Direction.BOTH) @Fetch
    private Election election;

    private static Logger LOG = LoggerFactory.getLogger(Position.class);

    public Position() {
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    public static Position fromPositionDetails(PositionDetails positionDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromPositionDetails()");
        Position position = new Position();
        if (LOG.isTraceEnabled()) LOG.trace("positionDetails "+positionDetails);
        position.setNodeId(positionDetails.getNodeId());
        position.setName(positionDetails.getName());
        position.setDescription(positionDetails.getDescription());
        position.election = new Election(positionDetails.getElectionId(), null, null, null, null, null, null,null,null);

        if (LOG.isTraceEnabled()) LOG.trace("position "+position);
        return position;
    }

    public PositionDetails toPositionDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toPositionDetails()");
        PositionDetails positionDetails = new PositionDetails();
        if (LOG.isTraceEnabled()) LOG.trace("position "+this);
        positionDetails.setNodeId(getNodeId());
        positionDetails.setName(getName());
        positionDetails.setDescription(getDescription());
        positionDetails.setElectionId(election.getNodeId());

        if (LOG.isTraceEnabled()) LOG.trace("positionDetails; "+ positionDetails);
        return positionDetails;
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
        buff.append(", election = ");
        buff.append(getElection());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
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

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (this.nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result
					+ ((election == null) ? 0 : election.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Position other = (Position) obj;
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

			if (description == null)
			{
				if (other.description != null) return false;
			}
			else if (!description.equals(other.description)) return false;
			if (election == null)
			{
				if (other.election != null) return false;
			}
			else if (!election.equals(other.election)) return false;
			if (name == null)
			{
				if (other.name != null) return false;
			}
			else if (!name.equals(other.name)) return false;
			if (nodeId == null)
			{
				if (other.nodeId != null) return false;
			}
			else if (!nodeId.equals(other.nodeId)) return false;
		}
		return true;
	}
}
