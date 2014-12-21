package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Yikai Gong
 */

public class CreateBadgeEvent extends CreateEvent{
    public CreateBadgeEvent(Details details) {
        super(details);
    }
}
