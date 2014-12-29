package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadBadgeEvent extends RequestReadEvent{
    public RequestReadBadgeEvent(Long badgeId) {
        super(badgeId);
    }
}
