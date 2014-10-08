package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.positions.*;
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
        return null;
    }

    @Override
    public ReadPositionEvent requestReadPosition(RequestReadPositionEvent requestReadPositionEvent) {
        return null;
    }

    @Override
    public PositionUpdatedEvent updatePosition(UpdatePositionEvent updatePositionEvent) {
        return null;
    }

    @Override
    public PositionDeletedEvent deletePosition(DeletePositionEvent deletePositionEvent) {
        return null;
    }
}
