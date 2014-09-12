package com.eulersbridge.iEngage.core.events.Elections;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CreateElectionEvent extends CreateEvent{
    private ElectionDetails electionDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreateElectionEvent.class);

    public CreateElectionEvent(Long id, ElectionDetails electionDetails){
        if (LOG.isDebugEnabled()) LOG.debug("CreateElection("+id+","+electionDetails+") = ");
        electionDetails.setElectionId(id);
        this.electionDetails = electionDetails;
    }

    public CreateElectionEvent(ElectionDetails electionDetails){
        if (LOG.isDebugEnabled()) LOG.debug("CreateElection("+electionDetails+") = ");
        this.electionDetails = electionDetails;
    }

    public ElectionDetails getElectionDetails(){
        return this.electionDetails;
    }

    public void setElectionDetails(ElectionDetails electionDetails){
        this.electionDetails = electionDetails;
    }

}
