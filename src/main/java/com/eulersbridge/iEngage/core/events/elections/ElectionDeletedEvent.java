package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class ElectionDeletedEvent extends DeletedEvent{
    private Long electionId;
    private boolean deletionCompleted = true;

    public ElectionDeletedEvent(Long electionId){
        this.electionId = electionId;
    }

    public Long getElectionId(){
        return this.electionId;
    }

    public static ElectionDeletedEvent deletionForbidden(Long electionId){
        ElectionDeletedEvent electionDeletedEvent = new ElectionDeletedEvent(electionId);
        electionDeletedEvent.entityFound = true;
        electionDeletedEvent.deletionCompleted = false;
        return electionDeletedEvent;
    }

    public static ElectionDeletedEvent notFound(Long electionId){
        ElectionDeletedEvent electionDeletedEvent = new ElectionDeletedEvent(electionId);
        electionDeletedEvent.entityFound = false;
        electionDeletedEvent.deletionCompleted = false;
        return electionDeletedEvent;
    }

    public boolean isDeletionCompleted(){
        return this.deletionCompleted;
    }

}
