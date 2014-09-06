package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.Elections.ReadElectionEvent;
import com.eulersbridge.iEngage.core.events.Elections.RequestReadElectionEvent;
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
            readElectionEvent = new ReadElectionEvent(requestReadElectionEvent.getElectionId(), election.toElectionDetail());
        }
        else{
            readElectionEvent = ReadElectionEvent.notFound(requestReadElectionEvent.getElectionId());
        }
        return readElectionEvent;
    }



}
