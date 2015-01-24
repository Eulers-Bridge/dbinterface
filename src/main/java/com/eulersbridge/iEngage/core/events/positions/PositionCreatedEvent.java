package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class PositionCreatedEvent extends CreatedEvent
{
    private static Logger LOG = LoggerFactory.getLogger(PositionCreatedEvent.class);

    public PositionCreatedEvent()
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public PositionCreatedEvent(PositionDetails positionDetails)
    {
    	super(positionDetails);
    }
}
