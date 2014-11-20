package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionCreatedEvent extends CreatedEvent{
    private Long PositionId;
    private PositionDetails positionDetails;

    private static Logger LOG = LoggerFactory.getLogger(PositionCreatedEvent.class);

    public PositionCreatedEvent(Long positionId) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        PositionId = positionId;
    }

    public PositionCreatedEvent(Long positionId, PositionDetails positionDetails) {
        this(positionId);
        this.positionDetails = positionDetails;
    }

    public Long getPositionId() {
        return PositionId;
    }

    public void setPositionId(Long positionId) {
        PositionId = positionId;
    }

    @Override
    public PositionDetails getDetails()
    {
        return positionDetails;
    }

    public PositionDetails getPositionDetails() {
        return positionDetails;
    }

    public void setPositionDetails(PositionDetails positionDetails) {
        this.positionDetails = positionDetails;
    }
}
