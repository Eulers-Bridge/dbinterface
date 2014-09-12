package com.eulersbridge.iEngage.core.events.Elections;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadElectionEvent extends ReadEvent{

    private Long electionId;
    private ElectionDetail electionDetail;

    public ReadElectionEvent (Long electionId){
        this.electionId = electionId;
    }
    public ReadElectionEvent (Long electionId, ElectionDetail electionDetail){
        this.electionId = electionId;
        this.electionDetail = electionDetail;
    }

    public Long getElectionId(){
        return this.electionId;
    }

    public ElectionDetail getElectionDetail(){
        return this.electionDetail;
    }

    public static ReadElectionEvent notFound(Long electionId){
        ReadElectionEvent ev = new ReadElectionEvent(electionId);
        ev.entityFound = false;
        return ev;
    }
}
