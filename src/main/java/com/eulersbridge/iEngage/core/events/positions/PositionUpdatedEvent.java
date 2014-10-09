package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class PositionUpdatedEvent extends UpdatedEvent{
    private Long positionId;
    private PositionDetails positionDetails;

    public PositionUpdatedEvent(Long positionId, PositionDetails positionDetails) {
        this.positionId = positionId;
        this.positionDetails = positionDetails;
    }

    public PositionUpdatedEvent(Long positionId) {
        this.positionId = positionId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public PositionDetails getPositionDetails() {
        return positionDetails;
    }

    public static PositionUpdatedEvent notFound(Long id){
        PositionUpdatedEvent positionUpdatedEvent = new PositionUpdatedEvent(id);
        positionUpdatedEvent.entityFound = false;
        return positionUpdatedEvent;
    }
}
