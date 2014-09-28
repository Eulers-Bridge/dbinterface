package com.eulersbridge.iEngage.core.events.elections;

/**
 * @author Yikai Gong
 */

public class UpdateElectionEvent {
    private Long electionId;
    private ElectionDetails electionDetails;

    public UpdateElectionEvent(Long electionId, ElectionDetails electionDetails){
        this.electionId = electionId;
        this.electionDetails = electionDetails;
        this.electionDetails.setElectionId(electionId);
    }

    public Long getElectionId(){
        return this.electionId;
    }

    public ElectionDetails getElectionDetails(){
        return this.electionDetails;
    }
}
