package com.eulersbridge.iEngage.core.events.positions;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class CreatePositionEvent extends CreateEvent{
    private PositionDetails positionDetails;

    private static Logger LOG = LoggerFactory.getLogger(CreatePositionEvent.class);

    public CreatePositionEvent(PositionDetails positionDetails) {
        this.positionDetails = positionDetails;
    }

    public CreatePositionEvent(Long id, PositionDetails positionDetails) {
        positionDetails.setPositionId(id);
        this.positionDetails = positionDetails;
    }

    public PositionDetails getPositionDetails() {
        return positionDetails;
    }

    public void setPositionDetails(PositionDetails positionDetails) {
        this.positionDetails = positionDetails;
    }
}
