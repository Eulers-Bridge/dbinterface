package com.eulersbridge.iEngage.core.events.Elections;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class ElectionUpdatedEvent extends UpdatedEvent {
    private Long electionId;
    private ElectionDetails electionDetails;

    public ElectionUpdatedEvent(Long electionId, ElectionDetails electionDetails){
        this.electionId = electionId;
        this.electionDetails = electionDetails;
    }

    public ElectionUpdatedEvent(Long electionId){
        this.electionId = electionId;
    }

    public Long getElectionId(){
        return this.electionId;
    }

    public ElectionDetails getElectionDetails(){
        return this.electionDetails;
    }

    public static ElectionUpdatedEvent notFound(Long id){
        ElectionUpdatedEvent electionUpdatedEvent = new ElectionUpdatedEvent(id);
        electionUpdatedEvent.entityFound = false;
        return electionUpdatedEvent;
    }

}
