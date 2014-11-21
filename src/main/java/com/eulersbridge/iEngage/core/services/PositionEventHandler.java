package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.positions.*;
import com.eulersbridge.iEngage.database.domain.Position;
import com.eulersbridge.iEngage.database.repository.PositionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionEventHandler implements PositionService{
    private static Logger LOG = LoggerFactory.getLogger(PositionService.class);

    private PositionRepository positionRepository;

    public PositionEventHandler(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public PositionCreatedEvent createPosition(CreatePositionEvent createPositionEvent) {
        PositionDetails positionDetails = (PositionDetails) createPositionEvent.getDetails();
        Position position = Position.fromPositionDetails(positionDetails);
        Position result = positionRepository.save(position);
        PositionCreatedEvent positionCreatedEvent = new PositionCreatedEvent(result.getPositionId(), result.toPositionDetails());
        return positionCreatedEvent;
    }

    @Override
    public ReadEvent requestReadPosition(RequestReadPositionEvent requestReadPositionEvent) {
        Position position = positionRepository.findOne(requestReadPositionEvent.getNodeId());
        ReadEvent readPositionEvent;
        if(position != null){
            readPositionEvent = new ReadPositionEvent(position.getPositionId(), position.toPositionDetails());
        }
        else{
            readPositionEvent = ReadPositionEvent.notFound(requestReadPositionEvent.getNodeId());
        }
        return readPositionEvent;
    }

    @Override
    public PositionUpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent) {
        PositionDetails positionDetails = updatePositionEvent.getPositionDetails();
        Position position = Position.fromPositionDetails(positionDetails);
        Long positionId = positionDetails.getPositionId();
        if(LOG.isDebugEnabled()) LOG.debug("positionId is " + positionId);
        Position positionOld = positionRepository.findOne(positionId);
        if(positionOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("position entity not found " + positionId);
            return PositionUpdatedEvent.notFound(positionId);
        }
        else{
            Position result = positionRepository.save(position);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getPositionId());
            return new PositionUpdatedEvent(result.getPositionId(), result.toPositionDetails());
        }
    }

    @Override
    public DeletedEvent deletePosition(DeletePositionEvent deletePositionEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deletePositionEvent= "+deletePositionEvent);
        Long positionId = deletePositionEvent.getPositionId();
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
