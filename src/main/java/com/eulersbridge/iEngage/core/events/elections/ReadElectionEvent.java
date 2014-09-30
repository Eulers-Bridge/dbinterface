package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadElectionEvent extends ReadEvent{

    private Long electionId;
    private ElectionDetails electionDetails;

    public ReadElectionEvent (Long electionId){
        this.electionId = electionId;
    }
    public ReadElectionEvent (Long electionId, ElectionDetails electionDetails){
        this.electionId = electionId;
        this.electionDetails = electionDetails;
    }

    public Long getElectionId(){
        return this.electionId;
    }

    public ElectionDetails getElectionDetails(){
        return this.electionDetails;
    }

    public static ReadElectionEvent notFound(Long electionId){
        ReadElectionEvent ev = new ReadElectionEvent(electionId);
        ev.entityFound = false;
        return ev;
    }
}
