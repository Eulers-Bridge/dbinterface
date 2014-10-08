package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class PositionDeletedEvent extends DeletedEvent{
    private final Long positionId;
    private boolean deletionCompleted = true;

    public PositionDeletedEvent(Long positionId) {
        this.positionId = positionId;
    }

    public static PositionDeletedEvent deletionForbidden(Long positionId){
        PositionDeletedEvent positionDeletedEvent = new PositionDeletedEvent(positionId);
        positionDeletedEvent.entityFound = true;
        positionDeletedEvent.deletionCompleted = false;
        return positionDeletedEvent;
    }

    public static PositionDeletedEvent notFound(Long positionId){
        PositionDeletedEvent positionDeletedEvent = new PositionDeletedEvent(positionId);
        positionDeletedEvent.entityFound = false;
        positionDeletedEvent.deletionCompleted = false;
        return positionDeletedEvent;
    }

    public boolean isDeletionCompleted(){
        return this.deletionCompleted;
    }
}
