package com.eulersbridge.iEngage.core.events.elections;


import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadElectionEvent extends RequestReadEvent{
    private Long electionId;

    public RequestReadElectionEvent(Long electionId){
        this.electionId = electionId;
    }

    public Long getElectionId(){
        return this.electionId;
    }

}
