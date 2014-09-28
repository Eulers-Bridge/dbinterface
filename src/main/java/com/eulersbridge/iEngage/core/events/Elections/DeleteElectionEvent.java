package com.eulersbridge.iEngage.core.events.Elections;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteElectionEvent extends DeleteEvent {

    private final Long electionId;

    public DeleteElectionEvent(final Long electionId){
        this.electionId = electionId;
    }

    public Long getElectionId(){
        return this.electionId;
    }
}
