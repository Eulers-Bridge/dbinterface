package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.positions.*;
import com.eulersbridge.iEngage.database.domain.Election;
import com.eulersbridge.iEngage.database.domain.Position;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionEventHandler implements PositionService{
    private static Logger LOG = LoggerFactory.getLogger(PositionService.class);

    private PositionRepository positionRepository;
    private ElectionRepository electionRepository;

    public PositionEventHandler(PositionRepository positionRepository,
			ElectionRepository electionRepository)
	{
        this.positionRepository = positionRepository;
		this.electionRepository = electionRepository;
	}

	@Override
    public CreatedEvent createPosition(CreatePositionEvent createPositionEvent)
	{
        PositionDetails positionDetails = (PositionDetails) createPositionEvent.getDetails();
        Position position = Position.fromPositionDetails(positionDetails);
        
    	if (LOG.isDebugEnabled()) LOG.debug("Finding election with nodeId = "+positionDetails.getElectionId());
    	Election elect=electionRepository.findOne(positionDetails.getElectionId());

    	PositionCreatedEvent positionCreatedEvent;
    	if (elect!=null)
    	{
    		position.setElection(elect);
            Position result = positionRepository.save(position);
            positionCreatedEvent = new PositionCreatedEvent(result.toPositionDetails());
    	}
    	else
    	{
    		positionCreatedEvent=PositionCreatedEvent.electionNotFound(positionDetails.getElectionId());
    	}
        return positionCreatedEvent;
    }

    @Override
    public ReadEvent readPosition(RequestReadPositionEvent requestReadPositionEvent) {
        Position position = positionRepository.findOne(requestReadPositionEvent.getNodeId());
        ReadEvent readPositionEvent;
        if(position != null){
            readPositionEvent = new PositionReadEvent(position.getNodeId(), position.toPositionDetails());
        }
        else{
            readPositionEvent = PositionReadEvent.notFound(requestReadPositionEvent.getNodeId());
        }
        return readPositionEvent;
    }

    @Override
    public UpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent) {
        PositionDetails positionDetails = (PositionDetails) updatePositionEvent.getDetails();
        Position position = Position.fromPositionDetails(positionDetails);
        Long positionId = positionDetails.getNodeId();
        if(LOG.isDebugEnabled()) LOG.debug("positionId is " + positionId);
        Position positionOld = positionRepository.findOne(positionId);
        if(positionOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("position entity not found " + positionId);
            return PositionUpdatedEvent.notFound(positionId);
        }
        else{
            Position result = positionRepository.save(position);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new PositionUpdatedEvent(result.getNodeId(), result.toPositionDetails());
        }
    }

    @Override
    public DeletedEvent deletePosition(DeletePositionEvent deletePositionEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePositionEvent= "+deletePositionEvent);
        Long positionId = deletePositionEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deletePosition("+positionId+")");
        Position position = positionRepository.findOne(positionId);
        if(position == null){
            return PositionDeletedEvent.notFound(positionId);
        }
        else{
            positionRepository.delete(positionId);
            PositionDeletedEvent positionDeletedEvent = new PositionDeletedEvent(positionId);
            return positionDeletedEvent;
        }
    }
}
