package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeletePositionEvent extends DeleteEvent{
    private final Long positionId;

    public DeletePositionEvent(Long positionId) {
        this.positionId = positionId;
    }

    public Long getPositionId() {
        return positionId;
    }
}
