package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdatePositionEvent extends UpdateEvent{
    private Long positionId;
    private PositionDetails positionDetails;

    public UpdatePositionEvent(Long positionId, PositionDetails positionDetails) {
        this.positionId = positionId;
        this.positionDetails = positionDetails;
        this.positionDetails.setPositionId(positionId);
    }

    public Long getPositionId() {
        return positionId;
    }

    public PositionDetails getPositionDetails() {
        return positionDetails;
    }
}
