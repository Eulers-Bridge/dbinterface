package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ElectionEventHandler implements ElectionService{

    private static Logger LOG = LoggerFactory.getLogger(ElectionEventHandler.class);

    private ElectionRepository eleRepository;

    public ElectionEventHandler(ElectionRepository electionRepository){
        this.eleRepository = electionRepository;
    }

    @Override
    public ReadElectionEvent requestReadElection(RequestReadElectionEvent requestReadElectionEvent){
        Election election = eleRepository.findOne(requestReadElectionEvent.getElectionId());
        ReadElectionEvent readElectionEvent;
        if (election!=null){
            readElectionEvent = new ReadElectionEvent(requestReadElectionEvent.getElectionId(), election.toElectionDetails());
        }
        else{
            readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getElectionId());
        }
        return readElectionEvent;
    }

    @Override
    public ElectionCreatedEvent createElection(CreateElectionEvent createElectionEvent){
        ElectionDetails electionDetails = createElectionEvent.getElectionDetails();
        Election election = Election.fromElectionDetails(electionDetails);
        Election result = eleRepository.save(election);
        ElectionCreatedEvent electionCreatedEvent = new ElectionCreatedEvent(result.getNodeId(), result.toElectionDetails());
        return electionCreatedEvent;
    }

    @Override
    public ReadElectionEvent readPreviousElection(RequestReadElectionEvent requestReadElectionEvent){
        Election election = eleRepository.findPreviousElection(requestReadElectionEvent.getElectionId());
        if (LOG.isDebugEnabled()) LOG.debug("election = "+election);
        ReadElectionEvent readElectionEvent;
        if (election!=null){
            readElectionEvent = new ReadElectionEvent(requestReadElectionEvent.getElectionId(), election.toElectionDetails());
        }
        else{
            readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getElectionId());
        }
        return readElectionEvent;
    }

    @Override
    public ReadElectionEvent readNextElection(RequestReadElectionEvent requestReadElectionEvent){
        Election election = eleRepository.findNextElection(requestReadElectionEvent.getElectionId());
        if (LOG.isDebugEnabled()) LOG.debug("election = "+election);
        ReadElectionEvent readElectionEvent;
        if (election!=null){
            readElectionEvent = new ReadElectionEvent(requestReadElectionEvent.getElectionId(), election.toElectionDetails());
        }
        else{
            readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getElectionId());
        }
        return readElectionEvent;
    }

    @Override
    public ElectionDeletedEvent deleteElection (DeleteElectionEvent deleteElectionEvent){
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteElectionEvent= "+deleteElectionEvent);
        Long electionId = deleteElectionEvent.getElectionId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteElection("+electionId+")");
        Election election = eleRepository.findOne(electionId);
        if(election == null){
            return ElectionDeletedEvent.notFound(electionId);
        }
        else{
            eleRepository.delete(electionId);
            ElectionDeletedEvent electionDeletedEvent = new ElectionDeletedEvent(electionId);
            return electionDeletedEvent;
        }
    }

    @Override
    public ElectionUpdatedEvent updateElection(UpdateElectionEvent updateElectionEvent) {
        ElectionDetails electionDetails = updateElectionEvent.getElectionDetails();
        Election election = Election.fromElectionDetails(electionDetails);
        Long electionId = electionDetails.getElectionId();
        if(LOG.isDebugEnabled()) LOG.debug("election Id is " + electionId);
        Election electionOld = eleRepository.findOne(electionId);
        if(electionOld ==null){
            if(LOG.isDebugEnabled()) LOG.debug("election entity not found " + electionId);
            return ElectionUpdatedEvent.notFound(electionId);
        }
        else{
            Election result = eleRepository.save(election);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new ElectionUpdatedEvent(result.getNodeId(), result.toElectionDetails());
        }
    }
}
