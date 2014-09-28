package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class ElectionCreatedEvent extends CreatedEvent{
    private ElectionDetails electionDetails;
    private Long id;

    public ElectionCreatedEvent (Long id, ElectionDetails electionDetails){
        this.electionDetails = electionDetails;
        this.id = id;
    }

    public ElectionCreatedEvent(Long id){
        this.id = id;
    }

    public Long getElectionId(){
        return this.id;
    }

    public void setKey(Long id){
        this.id = id;
    }

    public void setElectionDetails(ElectionDetails electionDetails){
        this.electionDetails = electionDetails;
    }

    public ElectionDetails getElectionDetails(){
        return this.electionDetails;
    }
}
