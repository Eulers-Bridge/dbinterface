package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadPositionEvent extends ReadEvent{
    private Long positionId;
    private PositionDetails positionDetails;

    public ReadPositionEvent(Long positionId) {
        this.positionId = positionId;
    }

    public ReadPositionEvent(Long positionId, PositionDetails positionDetails) {
        this.positionId = positionId;
        this.positionDetails = positionDetails;
    }

    public Long getPositionId() {
        return positionId;
    }

    public PositionDetails getPositionDetails() {
        return positionDetails;
    }

    public static ReadPositionEvent notFound(Long positionId){
        ReadPositionEvent readPositionEvent = new ReadPositionEvent(positionId);
        readPositionEvent.entityFound = false;
        return readPositionEvent;
    }
}
