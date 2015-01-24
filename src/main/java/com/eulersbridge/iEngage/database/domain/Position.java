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
    private Long positionId;
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
        position.setPositionId(positionDetails.getPositionId());
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
        positionDetails.setPositionId(getPositionId());
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
        buff.append(getPositionId());
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

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
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
}
