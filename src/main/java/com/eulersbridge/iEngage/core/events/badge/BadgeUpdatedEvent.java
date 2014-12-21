package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class BadgeUpdatedEvent extends UpdatedEvent{
    public BadgeUpdatedEvent(Long badgeId, BadgeDetails badgeDetails) {
        super(badgeId, badgeDetails);
    }

    public BadgeUpdatedEvent(Long badgeId) {
        super(badgeId);
    }
}
