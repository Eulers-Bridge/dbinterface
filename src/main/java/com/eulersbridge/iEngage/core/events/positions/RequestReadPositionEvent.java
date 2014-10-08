package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadPositionEvent extends RequestReadEvent{
    private Long positionId;

    public RequestReadPositionEvent(Long positionId) {
        this.positionId = positionId;
    }

    public Long getPositionId() {
        return positionId;
    }
}
