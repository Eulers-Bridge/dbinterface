package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionCreatedEvent extends CreatedEvent
{
    private Long positionId;

    private static Logger LOG = LoggerFactory.getLogger(PositionCreatedEvent.class);

    public PositionCreatedEvent(Long positionId)
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.positionId = positionId;
    }

    public PositionCreatedEvent(Long positionId, PositionDetails positionDetails)
    {
    	super(positionDetails);
        this.positionId = positionId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
}
